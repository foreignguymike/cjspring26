package com.distraction.cjspring26.tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Direction;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.entity.Collectible;
import com.distraction.cjspring26.entity.Ladder;

import java.util.ArrayList;
import java.util.List;

public class TileMap {

    private final Context context;
    private final TextureRegion grass;
    private final Tile[][] map;
    private final int tileSize = 192;

    private final List<Ladder> ladders;
    public final List<Collectible> collectibles;

    public TileMap(Context context) {
        this.context = context;
        grass = context.getImage("grass");
        ladders = new ArrayList<>();

        int[][] mapData = MapData.mapData;
        map = new Tile[mapData.length][mapData[0].length];
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                Tile tile = new Tile();
                int type = mapData[row][col];
                if (type == 0) tile.water = true;
                else if (type == 1) tile.grass = true;
                map[row][col] = tile;
            }
        }

        Utils.flip(map);

        collectibles = new ArrayList<>();
        collectibles.add(new Collectible(context, this, Collectible.Type.LADDER, 3, 8));
        collectibles.add(new Collectible(context, this, Collectible.Type.LADDER, 4, 15));
    }

    public int getTotalWidth() {
        return map[0].length * tileSize;
    }

    public int getTotalHeight() {
        return map.length * tileSize;
    }

    public boolean canBuild(int row, int col, Direction direction) {
        if (row < 0 || col < 0 || row >= map.length || col >= map[0].length) return false;
        if (map[row][col].anchor) return false;
        int r = row;
        int c = col;
        if (direction == Direction.UP) r++;
        else if (direction == Direction.DOWN) r--;
        else if (direction == Direction.LEFT) c--;
        else if (direction == Direction.RIGHT) c++;
        return map[r][c].canBuildBridge();
    }

    public boolean canWalk(int row, int col) {
        if (row < 0 || col < 0 || row >= map.length || col >= map[0].length) return false;
        return map[row][col].canWalk();
    }

    public boolean isAnchor(int row, int col) {
        for (Ladder ladder : ladders) {
            if (ladder.row == row && ladder.col == col) {
                return true;
            }
        }
        return false;
    }

    public void removeAnchor(int row, int col) {
        if (!map[row][col].anchor) return;
        Ladder removedLadder = null;
        for (int i = 0; i < ladders.size(); i++) {
            Ladder ladder = ladders.get(i);
            Direction direction = ladder.direction;
            if (ladder.row == row && ladder.col == col) {
                if (ladder.ladder == null) {
                    if (direction == Direction.UP) map[row + 1][col].bridge = false;
                    else if (direction == Direction.DOWN) map[row - 1][col].bridge = false;
                    else if (direction == Direction.LEFT) map[row][col - 1].bridge = false;
                    else if (direction == Direction.RIGHT) map[row][col + 1].bridge = false;
                    removedLadder = ladders.remove(i);
                }
                break;
            }
        }
        for (Ladder ladder : ladders) {
            if (ladder.ladder == removedLadder) {
                ladder.ladder = null;
            }
        }
        map[row][col].anchor = false;
    }

    public void addAnchor(int row, int col, Direction direction) {
        Ladder newLadder = new Ladder(context, this, direction, row, col);
        for (Ladder ladder : ladders) {
            if (ladder.contains(row, col)) {
                ladder.ladder = newLadder;
                break;
            }
        }
        ladders.add(newLadder);
        map[row][col].anchor = true;
        if (direction == Direction.UP) map[row + 1][col].bridge = true;
        else if (direction == Direction.DOWN) map[row - 1][col].bridge = true;
        else if (direction == Direction.LEFT) map[row][col - 1].bridge = true;
        else if (direction == Direction.RIGHT) map[row][col + 1].bridge = true;
    }

    public int coord(int tile) {
        return tile * tileSize + tileSize / 2;
    }

    public void update(float dt) {
        for (Collectible collectible : collectibles) {
            collectible.update(dt);
        }
    }

    public void render(SpriteBatch sb) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                Tile tile = map[row][col];
                if (tile.grass) {
                    sb.draw(grass, col * tileSize, row * tileSize);
                }
            }
        }
        for (Ladder ladder : ladders) {
            ladder.render(sb);
        }
        for (Collectible collectible : collectibles) {
            collectible.render(sb);
        }
    }

}

package com.distraction.cjspring26.tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Direction;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.entity.Ladder;

import java.util.ArrayList;
import java.util.List;

public class TileMap {

    private final Context context;
    private final TextureRegion grass;
    private final int[][] map;
    private final int tileSize = 192;

    private final List<Ladder> ladders;

    public TileMap(Context context) {
        this.context = context;
        grass = context.getImage("grass");
        ladders = new ArrayList<>();

        map = new int[][] {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        Utils.flip(map);
    }

    public boolean canBuild(int row, int col) {
        if (row < 0 || col < 0 || row >= map.length || col >= map[0].length) return false;
        return map[row][col] == 0;
    }

    public boolean isWater(int row, int col) {
        if (row < 0 || col < 0 || row >= map.length || col >= map[0].length) return true;
        return map[row][col] == 0;
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
        Ladder removedLadder = null;
        for (int i = 0; i < ladders.size(); i++) {
            Ladder ladder = ladders.get(i);
            Direction direction = ladder.direction;
            if (ladder.row == row && ladder.col == col) {
                if (ladder.ladder == null) {
                    if (direction == Direction.UP) map[row + 1][col] = 0;
                    else if (direction == Direction.DOWN) map[row - 1][col] = 0;
                    else if (direction == Direction.LEFT) map[row][col - 1] = 0;
                    else if (direction == Direction.RIGHT) map[row][col + 1] = 0;
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
        if (direction == Direction.UP) map[row + 1][col] = 2;
        else if (direction == Direction.DOWN) map[row - 1][col] = 2;
        else if (direction == Direction.LEFT) map[row][col - 1] = 2;
        else if (direction == Direction.RIGHT) map[row][col + 1] = 2;
    }

    public int coord(int tile) {
        return tile * tileSize + tileSize / 2;
    }

    public void render(SpriteBatch sb) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                int tile = map[row][col];
                if (tile == 1) {
                    sb.draw(grass, col * tileSize, row * tileSize);
                }
            }
        }
        for (Ladder ladder : ladders) {
            ladder.render(sb);
        }
    }

}

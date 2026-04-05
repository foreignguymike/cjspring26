package com.distraction.cjspring26.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Direction;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.entity.Collectible;
import com.distraction.cjspring26.entity.Player;
import com.distraction.cjspring26.entity.Stone;

import java.util.ArrayList;
import java.util.List;

public class TileMap {

    private final Tile[][] map;
    private final int tileSize = 192;

    // cache
    private final List<Stone> stones;
    public final List<Collectible> collectibles;

    public TileMap(Context context) {
        stones = new ArrayList<>();

        int[][] mapData = MapData.mapData;
        map = new Tile[mapData.length][mapData[0].length];
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                Tile tile = new Tile(context, this, map.length - row - 2, col);
                int type = mapData[row][col];
                if (type == 2) tile.water = true;
                else if (type == 1) tile.grass = true;
                else if (type == 3) {
                    tile.water = true;
                    tile.stone = false;
                    stones.add(new Stone(context, this, map.length - row - 2, col, false));
                } else if (type == 4) {
                    tile.water = true;
                    tile.stone = true;
                    stones.add(new Stone(context, this, map.length - row - 2, col, true));
                }
                map[row][col] = tile;
            }
        }

        Utils.flip(map);

        for (GridPoint2 toggle : MapData.toggles) {
            map[map.length - toggle.x - 2][toggle.y].stoneToggle = true;
        }

        collectibles = new ArrayList<>();
        for (GridPoint2 coin : MapData.coins) {
            collectibles.add(new Collectible(context, this, map.length - coin.x - 2, coin.y));
        }
    }

    public int getTotalWidth() {
        return map[0].length * tileSize;
    }

    public int getTotalHeight() {
        return map.length * tileSize;
    }

    public void playerLanded(Player player, int row, int col) {
        for (int i = 0; i < collectibles.size(); i++) {
            Collectible c = collectibles.get(i);
            if (player.collect(c)) {
                collectibles.remove(i);
                i--;
            }
        }
        if (map[row][col].stoneToggle) {
            toggle();
        }
    }

    public void toggle() {
        for (Stone s : stones) {
            s.toggle();
            map[s.row + 1][s.col].stone = !map[s.row + 1][s.col].stone;
        }
    }

    /**
     * how far the player can travel.
     * 0 if blocked.
     * 1 if next tile is walkable.
     * 2 if next tile is jumpable.
     */
    public int getTravelDistance(int row, int col, Direction direction) {
        int dr = direction == Direction.UP ? 1 : direction == Direction.DOWN ? -1 : 0;
        int dc = direction == Direction.LEFT ? -1 : direction == Direction.RIGHT ? 1 : 0;
        row += dr;
        col += dc;
        row += 1;
        if (row < 0 || col < 0 || row >= map.length || col >= map[0].length) return 0;
        if (map[row][col].canWalk()) return 1;
        row += dr;
        col += dc;
        if (row < 0 || col < 0 || row >= map.length || col >= map[0].length) return 0;
        if (map[row][col].canWalk()) return 2;
        return 0;
    }

    public int coord(int tile) {
        return tile * tileSize + tileSize / 2;
    }

    public void update(float dt) {
        for (Stone s : stones) s.update(dt);
        for (Collectible collectible : collectibles) collectible.update(dt);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        for (int row = map.length - 1; row >= 0; row--) {
            for (int col = 0; col < map[row].length; col++) {
                map[row][col].render(sb);
            }
        }
        for (Collectible collectible : collectibles) {
            collectible.render(sb);
        }
        for (int row = map.length - 1; row >= 0; row--) {
            for (int col = 0; col < map[row].length; col++) {
                map[row][col].renderFont(sb);
            }
        }
    }

}

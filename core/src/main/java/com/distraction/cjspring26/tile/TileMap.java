package com.distraction.cjspring26.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Direction;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.entity.Collectible;
import com.distraction.cjspring26.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TileMap {

    private final Context context;
    private final OrthographicCamera cam;
    private final OrthographicCamera uiCam;
    private int startRow;
    private int endRow;
    private int startCol;
    private int endCol;

    private final Tile[][] map;
    private final int tileSize = 128;

    // cache
    private final List<GridPoint2> platforms;
    public final List<Collectible> collectibles;

    public TileMap(Context context, OrthographicCamera cam, OrthographicCamera uiCam) {
        this.context = context;
        this.cam = cam;
        this.uiCam = uiCam;
        platforms = new ArrayList<>();

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
                    tile.platform = true;
                    tile.on = false;
                    platforms.add(new GridPoint2(map.length - row - 2, col));
                } else if (type == 4) {
                    tile.water = true;
                    tile.platform = true;
                    tile.on = true;
                    platforms.add(new GridPoint2(map.length - row - 2, col));
                }
                map[row][col] = tile;
            }
        }

        Utils.flip(map);

        for (GridPoint2 toggle : MapData.toggles) {
            map[map.length - toggle.x - 2][toggle.y].platformToggle = true;
        }

        collectibles = new ArrayList<>();
        int index = 0;
        for (GridPoint2 c : MapData.collectibles) {
            collectibles.add(new Collectible(context, this, map.length - c.x - 2, c.y, index++));
        }
    }

    public int getTotalWidth() {
        return map[0].length * tileSize;
    }

    public void playerLanded(Player player, int row, int col) {
        for (int i = 0; i < collectibles.size(); i++) {
            Collectible c = collectibles.get(i);
            if (c.row == row && c.col == col) {
                Vector3 pos = new Vector3(c.x, c.y, 0);
                cam.project(pos);
                uiCam.unproject(pos);
                player.collect(c, pos.x, pos.y);
                collectibles.remove(i);
                i--;
            }
        }
        if (map[row][col].platformToggle) {
            toggle();
        }
    }

    public void toggle() {
        context.audio.playSound("toggle", 0.3f);
        for (GridPoint2 s : platforms) {
            map[s.x + 1][s.y].toggle();
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
        float x = cam.position.x;
        float y= cam.position.y;
        startRow = MathUtils.clamp((int) (y / tileSize) + 6, 0, map.length - 1);
        endRow = MathUtils.clamp((int) (y / tileSize) - 4, 0, map.length - 1);
        startCol = MathUtils.clamp((int) (x / tileSize) - 10, 0, map[0].length - 1);
        endCol = MathUtils.clamp((int) (x / tileSize) + 10, 0, map[0].length);
        for (int row = startRow; row >= endRow; row--) {
            for (int col = startCol; col < endCol; col++) {
                map[row][col].update(dt);
            }
        }
        for (Collectible collectible : collectibles) collectible.update(dt);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        for (int row = startRow; row >= endRow; row--) {
            for (int col = startCol; col < endCol; col++) {
                map[row][col].render(sb);
            }
        }
    }

    public void renderCollectibles(SpriteBatch sb) {
        for (Collectible collectible : collectibles) {
            collectible.render(sb);
        }
    }

}

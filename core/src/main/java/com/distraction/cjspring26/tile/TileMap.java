package com.distraction.cjspring26.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Direction;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.entity.Anchor;
import com.distraction.cjspring26.entity.Collectible;
import com.distraction.cjspring26.entity.Stone;

import java.util.ArrayList;
import java.util.List;

public class TileMap {

    private final Context context;
    private final Tile[][] map;
    private final int tileSize = 192;

    private final List<Anchor> anchors;
    private final List<Stone> stones;
    public final List<Collectible> collectibles;

    public TileMap(Context context) {
        this.context = context;
        anchors = new ArrayList<>();
        stones = new ArrayList<>();

        int[][] mapData = MapData.mapData;
        map = new Tile[mapData.length][mapData[0].length];
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                Tile tile = new Tile(context, this, map.length - row - 1, col);
                int type = mapData[row][col];
                if (type == 0) tile.water = true;
                else if (type == 1) tile.grass = true;
                else if (type == 2) {
                    tile.water = true;
                    stones.add(new Stone(context, this, map.length - row - 1, col, false));
                } else if (type == 3) {
                    tile.water = true;
                    stones.add(new Stone(context, this, map.length - row - 1, col, true));
                } else if (type == 4) {
                    tile.grass = true;
                    tile.stoneToggle = true;
                }
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

    public void playerLanded(int row, int col) {
        if (map[row][col].stoneToggle) {
            toggle();
        }
    }

    public void toggle() {
        for (Stone s : stones) {
            s.toggle();
            map[s.row][s.col].stone = !map[s.row][s.col].stone;
            // find any anchors on this stone, toggle them and all chains
            for (Anchor anchor : anchors) {
                if (anchor.row == s.row && anchor.col == s.col) {
                    chainToggle(anchor);
                }
            }
        }
    }

    private void chainToggle(Anchor anchor) {
        anchor.toggle();
        int row = anchor.row;
        int col = anchor.col;
        Direction direction = anchor.direction;
        map[row][col].anchor = !map[row][col].anchor;
        if (direction == Direction.UP) map[row + 1][col].ladder = map[row][col].anchor;
        else if (direction == Direction.DOWN) map[row - 1][col].ladder = map[row][col].anchor;
        else if (direction == Direction.LEFT) map[row][col - 1].ladder = map[row][col].anchor;
        else if (direction == Direction.RIGHT) map[row][col + 1].ladder = map[row][col].anchor;
        if (anchor.anchor != null) {
            chainToggle(anchor.anchor);
        }
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
        return map[r][c].canBuildLadder();
    }

    public boolean canWalk(int row, int col) {
        if (row < 0 || col < 0 || row >= map.length || col >= map[0].length) return false;
        return map[row][col].canWalk();
    }

    public boolean isAnchor(int row, int col) {
        for (Anchor anchor : anchors) {
            if (anchor.row == row && anchor.col == col) {
                return true;
            }
        }
        return false;
    }

    // can only remove if it's the end chain (ie no ladder on ladder)
    public boolean removeAnchor(int row, int col) {
        if (!map[row][col].anchor) return false;
        boolean ret = false;
        Anchor removedAnchor = null;
        for (int i = 0; i < anchors.size(); i++) {
            Anchor anchor = anchors.get(i);
            Direction direction = anchor.direction;
            if (anchor.row == row && anchor.col == col) {
                if (anchor.anchor == null) {
                    if (direction == Direction.UP) map[row + 1][col].ladder = false;
                    else if (direction == Direction.DOWN) map[row - 1][col].ladder = false;
                    else if (direction == Direction.LEFT) map[row][col - 1].ladder = false;
                    else if (direction == Direction.RIGHT) map[row][col + 1].ladder = false;
                    removedAnchor = anchors.remove(i);
                    map[row][col].anchor = false;
                    ret = true;
                }
                break;
            }
        }
        // break previous chain
        for (Anchor anchor : anchors) {
            if (anchor.anchor == removedAnchor) {
                anchor.anchor = null;
            }
        }
        return ret;
    }

    public void addAnchor(int row, int col, Direction direction) {
        Anchor newAnchor = new Anchor(context, this, direction, row, col);
        for (Anchor anchor : anchors) {
            if (anchor.contains(row, col)) {
                anchor.anchor = newAnchor; // chain
                break;
            }
        }
        anchors.add(newAnchor);
        map[row][col].anchor = true;
        if (direction == Direction.UP) map[row + 1][col].ladder = true;
        else if (direction == Direction.DOWN) map[row - 1][col].ladder = true;
        else if (direction == Direction.LEFT) map[row][col - 1].ladder = true;
        else if (direction == Direction.RIGHT) map[row][col + 1].ladder = true;
    }

    public int coord(int tile) {
        return tile * tileSize + tileSize / 2;
    }

    public void update(float dt) {
        for (Stone s : stones) s.update(dt);
        for (Anchor a : anchors) a.update(dt);
        for (Collectible collectible : collectibles) collectible.update(dt);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        for (int row = map.length - 1; row >= 0; row--) {
            for (int col = 0; col < map[row].length; col++) {
                map[row][col].render(sb);
            }
        }
        for (Stone stone : stones) {
            stone.render(sb);
        }
        for (Anchor anchor : anchors) {
            anchor.render(sb);
        }
        for (Collectible collectible : collectibles) {
            collectible.render(sb);
        }
//        for (int row = map.length - 1; row >= 0; row--) {
//            for (int col = 0; col < map[row].length; col++) {
//                map[row][col].renderFont(sb);
//            }
//        }
    }

}

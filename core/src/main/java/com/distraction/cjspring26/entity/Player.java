package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Direction;
import com.distraction.cjspring26.util.Utils;
import com.distraction.cjspring26.tile.TileMap;

public class Player extends TileEntity {

    private static final float SPEED = 500;

    private final TextureRegion playerSkinImage;

    private boolean moving;
    private Direction direction = Direction.RIGHT;

    private boolean jumping;
    private float jumpy;
    private float totalDistance;

    public boolean up, down, left, right;
    public boolean mirror;

    public Inventory inventory;

    private final float offsety = 20;

    public Player(Context context, TileMap tileMap) {
        super(context, tileMap);
        image = context.getImage("playeroutline");
        playerSkinImage = context.getImage("playerskin");
        inventory = new Inventory(context);
    }

    public void reset() {
        up = down = left = right = false;
    }

    public void collect(Collectible c, float x, float y) {
        inventory.collect(c.index, x, y);
    }

    @Override
    public void update(float dt) {
        // can only move if not yet moving
        if (!moving) {
            int dist = 0;
            if (up) {
                direction = Direction.UP;
                dist = tileMap.getTravelDistance(row, col, direction);
                if (dist > 0) {
                    tileMap.playerLeft(row, col);
                    row += dist;
                    ydest = tileMap.coord(row);
                    totalDistance = getRemainingDistanceM();
                    dx = 0;
                    dy = SPEED * (dist > 1 ? 1.2f : 1);
                }
            } else if (down) {
                direction = Direction.DOWN;
                dist = tileMap.getTravelDistance(row, col, direction);
                if (dist > 0) {
                    tileMap.playerLeft(row, col);
                    row -= dist;
                    ydest = tileMap.coord(row);
                    totalDistance = getRemainingDistanceM();
                    dx = 0;
                    dy = -SPEED * (dist > 1 ? 1.2f : 1);
                }
            } else if (left) {
                mirror = true;
                direction = Direction.LEFT;
                dist = tileMap.getTravelDistance(row, col, direction);
                if (dist > 0) {
                    tileMap.playerLeft(row, col);
                    col -= dist;
                    xdest = tileMap.coord(col);
                    totalDistance = getRemainingDistanceM();
                    dx = -SPEED * (dist > 1 ? 1.2f : 1);
                    dy = 0;
                }
            } else if (right) {
                mirror = false;
                direction = Direction.RIGHT;
                dist = tileMap.getTravelDistance(row, col, direction);
                if (dist > 0) {
                    tileMap.playerLeft(row, col);
                    col += dist;
                    xdest = tileMap.coord(col);
                    totalDistance = getRemainingDistanceM();
                    dx = SPEED * (dist > 1 ? 1.2f : 1);
                    dy = 0;
                }
            } else {
                dx = dy = 0;
            }
            jumping = dist > 1;
        }

        boolean reachedDestination = atDestination();

        // move
        move(dt);

        // just reached destination
        if (!reachedDestination && atDestination()) {
            tileMap.playerLanded(this, row, col);
        }

        // prevent moving while already moving or changing direction
        moving = x != xdest || y != ydest;

        // calculate jump
        if (!moving) jumping = false;
        if (jumping) {
            jumpy = 100 * MathUtils.sin(3.14f * getRemainingDistanceM() / totalDistance);
        }

        inventory.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        if (mirror) {
            Utils.drawCenteredFlipped(sb, playerSkinImage, x, y + jumpy + offsety);
            Utils.drawCenteredFlipped(sb, image, x, y + jumpy + offsety);
        } else {
            Utils.drawCentered(sb, playerSkinImage, x, y + jumpy + offsety);
            Utils.drawCentered(sb, image, x, y + jumpy + offsety);
        }
    }
}

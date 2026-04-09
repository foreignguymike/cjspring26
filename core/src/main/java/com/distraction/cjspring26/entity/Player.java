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
    private static final float OFFSET_Y = 20;

    private final TextureRegion playerSkinImage;

    private boolean moving;

    private boolean jumping;
    private float jumpy;
    private float totalDistance;

    public boolean up, down, left, right;
    public boolean mirror;

    public Player(Context context, TileMap tileMap) {
        super(context, tileMap);
        image = context.getImage("playeroutline");
        playerSkinImage = context.getImage("playerskin");
    }

    public void reset() {
        up = down = left = right = false;
    }

    @Override
    public void update(float dt) {
        // can only move if not yet moving
        if (!moving) {
            int dist = 0;
            if (up) {
                dist = tileMap.getTravelDistance(row, col, Direction.UP);
                if (dist > 0) {
                    tileMap.playerLeft(row, col);
                    row += dist;
                    ydest = tileMap.coord(row);
                    totalDistance = getRemainingDistanceM();
                    dx = 0;
                    dy = SPEED * (dist > 1 ? 1.2f : 1);
                }
            } else if (down) {
                dist = tileMap.getTravelDistance(row, col, Direction.DOWN);
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
                dist = tileMap.getTravelDistance(row, col, Direction.LEFT);
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
                dist = tileMap.getTravelDistance(row, col, Direction.RIGHT);
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
            tileMap.playerLanded(row, col);
        }

        // prevent moving while already moving or changing direction
        moving = x != xdest || y != ydest;

        // calculate jump
        if (!moving) jumping = false;
        if (jumping) {
            jumpy = 100 * MathUtils.sin(3.14f * getRemainingDistanceM() / totalDistance);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (mirror) {
            Utils.drawCenteredFlipped(sb, playerSkinImage, x, y + jumpy + OFFSET_Y);
            Utils.drawCenteredFlipped(sb, image, x, y + jumpy + OFFSET_Y);
        } else {
            Utils.drawCentered(sb, playerSkinImage, x, y + jumpy + OFFSET_Y);
            Utils.drawCentered(sb, image, x, y + jumpy + OFFSET_Y);
        }
    }
}

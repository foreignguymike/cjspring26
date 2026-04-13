package com.distraction.cjspring26.entity.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Direction;
import com.distraction.cjspring26.entity.TileEntity;
import com.distraction.cjspring26.tile.TileMap;

public class Player extends TileEntity {

    private static final float SPEED = 500;
    public static final float OFFSET_Y = 30;

    public boolean moving;

    public boolean jumping;
    public float jumpy;
    private float totalDistance;

    public boolean up, down, left, right;
    public boolean mirror;

    public PlayerRenderer playerRenderer;

    public Player(Context context, TileMap tileMap) {
        super(context, tileMap);
        w = 128;
        h = 128;

        playerRenderer = new PlayerRenderer(context, this);
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
                    ydest = tileMap.coordRow(row);
                    totalDistance = getRemainingDistanceM();
                    dx = 0;
                    dy = SPEED * (dist > 1 ? 1.2f : 1);
                }
            } else if (down) {
                dist = tileMap.getTravelDistance(row, col, Direction.DOWN);
                if (dist > 0) {
                    tileMap.playerLeft(row, col);
                    row -= dist;
                    ydest = tileMap.coordRow(row);
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
                    xdest = tileMap.coordCol(col);
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
                    xdest = tileMap.coordCol(col);
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
        jumpy = (jumping ? 120 : 30) * MathUtils.sin(3.14f * getRemainingDistanceM() / totalDistance);
    }

    @Override
    public void render(SpriteBatch sb) {
        playerRenderer.render(sb);
    }
}

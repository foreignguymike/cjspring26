package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Direction;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.tile.TileMap;

public class Player extends TileEntity {

    private static final float SPEED = 500;

    private boolean moving;
    private Direction direction = Direction.RIGHT;

    private boolean jumping;
    private float jumpy;
    private float totalDistance;

    public boolean up, down, left, right;

    public Inventory inventory;

    public Player(Context context, TileMap tileMap) {
        super(context, tileMap);
        image = context.getImage("player");

        setTile(9, 0);

        inventory = new Inventory(context);

        dx = dy = SPEED;
    }

    @Override
    protected void setTile(int row, int col) {
        super.setTile(row, col);
        xdest = x;
        ydest = y;
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
                    row -= dist;
                    ydest = tileMap.coord(row);
                    totalDistance = getRemainingDistanceM();
                    dx = 0;
                    dy = -SPEED * (dist > 1 ? 1.2f : 1);
                }
            } else if (left) {
                direction = Direction.LEFT;
                dist = tileMap.getTravelDistance(row, col, direction);
                if (dist > 0) {
                    col -= dist;
                    xdest = tileMap.coord(col);
                    totalDistance = getRemainingDistanceM();
                    dx = -SPEED * (dist > 1 ? 1.2f : 1);
                    dy = 0;
                }
            } else if (right) {
                direction = Direction.RIGHT;
                dist = tileMap.getTravelDistance(row, col, direction);
                if (dist > 0) {
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
        Utils.drawCentered(sb, image, x, y + jumpy);
        if (direction == Direction.UP) Utils.drawCentered(sb, image, x, y + 50 + jumpy);
        else if (direction == Direction.DOWN) Utils.drawCentered(sb, image, x, y - 50 + jumpy);
        else if (direction == Direction.LEFT) Utils.drawCentered(sb, image, x - 50, y + jumpy);
        else if (direction == Direction.RIGHT) Utils.drawCentered(sb, image, x + 50, y + jumpy);
    }
}

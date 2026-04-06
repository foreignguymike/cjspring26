package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Direction;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.tile.TileMap;

public class Player extends TileEntity {

    private static final float SPEED = 600;

    private float xdest, ydest;
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
    }

    @Override
    protected void setTile(int row, int col) {
        super.setTile(row, col);
        xdest = x;
        ydest = y;
    }

    public boolean collect(Collectible c) {
        if (row != c.row || col != c.col) return false;
        inventory.increment();
        return true;
    }

    private float getRemainingDistance() {
        return Math.abs(x - xdest) + Math.abs(y - ydest);
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
                    totalDistance = getRemainingDistance();
                }
            } else if (down) {
                direction = Direction.DOWN;
                dist = tileMap.getTravelDistance(row, col, direction);
                if (dist > 0) {
                    row -= dist;
                    ydest = tileMap.coord(row);
                    totalDistance = getRemainingDistance();
                }
            } else if (left) {
                direction = Direction.LEFT;
                dist = tileMap.getTravelDistance(row, col, direction);
                if (dist > 0) {
                    col -= dist;
                    xdest = tileMap.coord(col);
                    totalDistance = getRemainingDistance();
                }
            } else if (right) {
                direction = Direction.RIGHT;
                dist = tileMap.getTravelDistance(row, col, direction);
                if (dist > 0) {
                    col += dist;
                    xdest = tileMap.coord(col);
                    totalDistance = getRemainingDistance();
                }
            }
            jumping = dist > 1;
        }

        boolean reachedDestination = x == xdest && y == ydest;

        // move
        float speed = jumping ? 1.5f * SPEED : SPEED;
        if (x < xdest) {
            x += speed * dt;
            if (x > xdest) x = xdest;
        } else if (x > xdest) {
            x -= speed * dt;
            if (x < xdest) x = xdest;
        }
        if (y < ydest) {
            y += speed * dt;
            if (y > ydest) y = ydest;
        } else if (y > ydest) {
            y -= speed * dt;
            if (y < ydest) y = ydest;
        }

        // just reached destination
        if (!reachedDestination && x == xdest && y == ydest) {
            tileMap.playerLanded(this, row, col);
        }

        // prevent moving while already moving or changing direction
        moving = x != xdest || y != ydest;

        // calculate jump
        if (!moving) jumping = false;
        if (jumping) {
            jumpy = 100 * MathUtils.sin(3.14f * getRemainingDistance() / totalDistance);
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

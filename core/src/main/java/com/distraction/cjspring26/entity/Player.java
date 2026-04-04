package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Direction;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.tile.TileMap;

public class Player extends TileEntity {

    private float xdest, ydest;
    private boolean moving;
    private float speed = 500;
    private Direction direction = Direction.RIGHT;
    private float directionTime = 0;

    public boolean up, down, left, right;

    public Player(Context context, TileMap tileMap) {
        super(tileMap);
        image = context.getImage("player");

        setTile(1, 1);
    }

    @Override
    protected void setTile(int row, int col) {
        super.setTile(row, col);
        xdest = x;
        ydest = y;
    }

    public void action() {
        if (moving) return;
        if (tileMap.isAnchor(row, col)) {
            tileMap.removeAnchor(row, col);
        } else {
            int r = row;
            int c = col;
            if (direction == Direction.UP) r++;
            else if (direction == Direction.DOWN) r--;
            else if (direction == Direction.LEFT) c--;
            else if (direction == Direction.RIGHT) c++;
            if (tileMap.canBuild(r, c)) {
                tileMap.addAnchor(row, col, direction);
            }
        }
    }

    @Override
    public void update(float dt) {
        // can only move if not yet moving
        if (!moving) {
            Direction oldDirection = direction;
            if (up) direction = Direction.UP;
            else if (down) direction = Direction.DOWN;
            else if (left) direction = Direction.LEFT;
            else if (right) direction = Direction.RIGHT;
            if (direction != oldDirection) {
                directionTime = 0.15f;
            } else {
                if (up && !tileMap.isWater(row + 1, col)) {
                    row++;
                    direction = Direction.UP;
                    ydest = tileMap.coord(row);
                } else if (down && !tileMap.isWater(row - 1, col)) {
                    row--;
                    direction = Direction.DOWN;
                    ydest = tileMap.coord(row);
                } else if (left && !tileMap.isWater(row, col - 1)) {
                    col--;
                    direction = Direction.LEFT;
                    xdest = tileMap.coord(col);
                } else if (right && !tileMap.isWater(row, col + 1)) {
                    col++;
                    direction = Direction.RIGHT;
                    xdest = tileMap.coord(col);
                }
            }
        }

        // move
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

        if (directionTime > 0) directionTime -= dt;

        // prevent moving while already moving or changing direction
        moving = x != xdest || y != ydest || directionTime > 0;
    }

    @Override
    public void render(SpriteBatch sb) {
        Utils.drawCentered(sb, image, x, y);
        if (direction == Direction.UP) Utils.drawCentered(sb, image, x, y + 50);
        else if (direction == Direction.DOWN) Utils.drawCentered(sb, image, x, y - 50);
        else if (direction == Direction.LEFT) Utils.drawCentered(sb, image, x - 50, y);
        else if (direction == Direction.RIGHT) Utils.drawCentered(sb, image, x + 50, y);
    }
}

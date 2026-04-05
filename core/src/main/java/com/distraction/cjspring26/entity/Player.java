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

    public Inventory inventory;

    public Player(Context context, TileMap tileMap) {
        super(tileMap);
        image = context.getImage("player");

        setTile(0, 14);

        inventory = new Inventory(context);
    }

    @Override
    protected void setTile(int row, int col) {
        super.setTile(row, col);
        xdest = x;
        ydest = y;
    }

    public void action() {
        if (moving) return;
        if (tileMap.isAnchor(row, col) && inventory.canAddLadder()) {
            if (tileMap.removeAnchor(row, col)) {
                inventory.addLadder();
            }
        } else if (inventory.isLadderSelected()) {
            if (tileMap.canBuild(row, col, direction)) {
                tileMap.addAnchor(row, col, direction);
                inventory.removeLadder();
            }
        }
    }

    public boolean collect(Collectible c) {
        if (row != c.row || col != c.col) return false;
        if (c.type == Collectible.Type.LADDER) {
            if (inventory.canAddLadder()) {
                inventory.addLadder();
                return true;
            } else {
                return false;
            }
        }
        return true;
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
                directionTime = 0; // make it higher if you want delay
            } else {
                if (up && tileMap.canWalk(row + 1, col)) {
                    row++;
                    direction = Direction.UP;
                    ydest = tileMap.coord(row);
                } else if (down && tileMap.canWalk(row - 1, col)) {
                    row--;
                    direction = Direction.DOWN;
                    ydest = tileMap.coord(row);
                } else if (left && tileMap.canWalk(row, col - 1)) {
                    col--;
                    direction = Direction.LEFT;
                    xdest = tileMap.coord(col);
                } else if (right && tileMap.canWalk(row, col + 1)) {
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

package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Direction;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.tile.TileMap;

public class Ladder extends TileEntity {

    private final TextureRegion anchor;
    public final Direction direction;
    public Ladder ladder = null;

    public Ladder(Context context, TileMap tileMap, Direction direction, int row, int col) {
        super(tileMap);

        this.anchor = context.getImage("anchor");
        this.image = context.getImage("ladder");
        this.direction = direction;
        setTile(row, col);
    }

    public boolean contains(int row, int col) {
        if (direction == Direction.UP) return row == this.row + 1 && col == this.col;
        else if (direction == Direction.DOWN) return row == this.row - 1 && col == this.col;
        else if (direction == Direction.LEFT) return row == this.row && col == this.col - 1;
        else if (direction == Direction.RIGHT) return row == this.row && col == this.col + 1;
        return false;
    }

    @Override
    public void render(SpriteBatch sb) {
        Utils.drawCentered(sb, anchor, x, y, direction.deg);
        if (direction == Direction.UP) Utils.drawCentered(sb, image, x, tileMap.coord(row + 1), direction.deg);
        else if (direction == Direction.DOWN) Utils.drawCentered(sb, image, x, tileMap.coord(row - 1), direction.deg);
        else if (direction == Direction.LEFT) Utils.drawCentered(sb, image, tileMap.coord(col - 1), y, direction.deg);
        else if (direction == Direction.RIGHT) Utils.drawCentered(sb, image, tileMap.coord(col + 1), y, direction.deg);
    }
}

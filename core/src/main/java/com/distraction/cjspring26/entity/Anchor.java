package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Direction;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.tile.TileMap;

public class Anchor extends TileEntity {

    private final TextureRegion ladderImage;
    public final Direction direction;
    public Anchor anchor = null;

    public Anchor(Context context, TileMap tileMap, Direction direction, int row, int col) {
        super(tileMap);

        this.image = context.getImage("anchor");
        this.ladderImage = context.getImage("ladder");
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
        if (!on) sb.setColor(Constants.DARK_PURPLE);
        Utils.drawCentered(sb, image, x, y + yoffset, direction.deg);
        if (direction == Direction.UP) Utils.drawCentered(sb, ladderImage, x, tileMap.coord(row + 1) + yoffset, direction.deg);
        else if (direction == Direction.DOWN) Utils.drawCentered(sb, ladderImage, x, tileMap.coord(row - 1) + yoffset, direction.deg);
        else if (direction == Direction.LEFT) Utils.drawCentered(sb, ladderImage, tileMap.coord(col - 1) + yoffset, y, direction.deg);
        else if (direction == Direction.RIGHT) Utils.drawCentered(sb, ladderImage, tileMap.coord(col + 1) + yoffset, y, direction.deg);
        sb.setColor(Color.WHITE);
    }
}

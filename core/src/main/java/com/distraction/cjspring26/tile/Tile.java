package com.distraction.cjspring26.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.entity.TileEntity;

public class Tile extends TileEntity {

    public boolean water;
    public boolean grass;
    public boolean stone;
    public boolean stoneToggle;

    private final TextureRegion grassImage;
    private final TextureRegion stoneImage;
    private final TextureRegion stoneToggleImage;
    private final BitmapFont font;

    public Tile(Context context, TileMap tileMap, int row, int col) {
        super(tileMap);
        setTile(row, col);
        grassImage = context.getImage("grass");
        stoneImage = context.getImage("stone");
        stoneToggleImage = context.getImage("stonetoggle");
        font = context.getFont();
    }

    public boolean canWalk() {
        return grass || stone;
    }

    public void render(SpriteBatch sb) {
        if (grass) Utils.drawCentered(sb, grassImage, x, y - 42);
        if (stone) Utils.drawCentered(sb, stoneImage, x, y + yoffset - 42);
        if (stoneToggle) Utils.drawCentered(sb, stoneToggleImage, x, y + 192);
    }

}

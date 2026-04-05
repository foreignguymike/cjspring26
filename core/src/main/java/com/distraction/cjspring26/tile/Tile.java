package com.distraction.cjspring26.tile;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.entity.TileEntity;

public class Tile extends TileEntity {

    public boolean water;
    public boolean grass;
    public boolean ladder;
    public boolean anchor;
    public boolean stone;

    private final TextureRegion grassImage;
    private final BitmapFont font;

    public Tile(Context context, TileMap tileMap, int row, int col) {
        super(tileMap);
        setTile(row, col);
        grassImage = context.getImage("grass");
        font = context.getFont();
    }

    public boolean canBuildLadder() {
        return water && !grass && !ladder && !stone;
    }

    public boolean canWalk() {
        return grass || ladder || stone;
    }

    public void render(SpriteBatch sb) {
        if (grass) Utils.drawCentered(sb, grassImage, x, y - 42);
    }

    public void renderFont(SpriteBatch sb) {
        String s = "";
        if (water) s += "w";
        if (grass) s += "g";
        if (ladder) s += "l";
        if (anchor) s += "a";
        if (stone) s += "s";
        font.draw(sb, s, x - 90, y + 100);
    }

}

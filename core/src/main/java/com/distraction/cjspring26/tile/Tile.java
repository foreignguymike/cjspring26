package com.distraction.cjspring26.tile;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.util.Utils;
import com.distraction.cjspring26.entity.TileEntity;

public class Tile extends TileEntity {

    public boolean water;
    public boolean grass;
    public boolean platform;
    public boolean platformToggle;
    public boolean strawberryToggle;

    private final TextureRegion grassImage;
    private final TextureRegion platformImage;
    private final TextureRegion platformOffImage;
    private final TextureRegion platformToggleImage;
    private final TextureRegion strawberryToggleImage;

    public Tile(Context context, TileMap tileMap, int row, int col) {
        super(context, tileMap);
        setTile(row, col);
        grassImage = context.getImage("grass");
        platformImage = context.getImage("grass");
        platformOffImage = context.getImage("platformoff");
        platformToggleImage = context.getImage("platformtoggle");
        strawberryToggleImage = context.getImage("strawberrytoggle");
    }

    public boolean canWalk() {
        return (grass || platform) && on;
    }

    public void render(SpriteBatch sb) {
        if (grass) {
            Utils.drawCentered(sb, platformImage, x, y - 42, toggleScale);
        }
        if (platform) {
            Utils.drawCentered(sb, platformImage, x, y - 42, toggleScale);
            if (!on && toggleTime <= 0) Utils.drawCentered(sb, platformOffImage, x, y - 23);
        }
        if (platformToggle) Utils.drawCentered(sb, platformToggleImage, x, y + 110);
        if (strawberryToggle) Utils.drawCentered(sb, strawberryToggleImage, x, y + 110);
    }

}

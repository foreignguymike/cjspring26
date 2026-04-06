package com.distraction.cjspring26.tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.entity.TileEntity;

public class Tile extends TileEntity {

    public boolean water;
    public boolean grass;
    public boolean platform;
    public boolean platformToggle;

    private final TextureRegion grassImage;
    private final TextureRegion platformImage;
    private final TextureRegion platformOffImage;
    private final TextureRegion platformToggleImage;

    public Tile(Context context, TileMap tileMap, int row, int col) {
        super(context, tileMap);
        setTile(row, col);
        grassImage = context.getImage("grass");
        platformImage = context.getImage("grass");
        platformOffImage = context.getImage("platformoff");
        platformToggleImage = context.getImage("platformtoggle");
    }

    public boolean canWalk() {
        return grass || (platform && on);
    }

    public void render(SpriteBatch sb) {
        if (grass) Utils.drawCentered(sb, grassImage, x, y - 42);
        if (platform) {
            Utils.drawCentered(sb, platformImage, x, y - 42, toggleScale);
            if (!on && toggleTime <= 0) Utils.drawCentered(sb, platformOffImage, x, y - 23);
        }
        if (platformToggle) Utils.drawCentered(sb, platformToggleImage, x, y + 106);
    }

}

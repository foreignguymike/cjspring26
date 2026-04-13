package com.distraction.cjspring26.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.util.Utils;
import com.distraction.cjspring26.entity.TileEntity;

public class Tile extends TileEntity {

    public boolean water;
    public boolean grass;
    public boolean platform;
    public boolean platformToggle;
    public boolean strawberryToggle;

    private final TextureRegion pixel;
    private final TextureRegion platformOffImage;
    private final TextureRegion strawberryToggleImage;

    public Tile(Context context, TileMap tileMap, int row, int col) {
        super(context, tileMap);
        setTile(row, col);
        pixel = context.getPixel();
        platformOffImage = context.getImage("platformoff");
        strawberryToggleImage = context.getImage("strawberrytoggle");
    }

    public boolean canWalk() {
        return (grass || platform) && on;
    }

    public void render(SpriteBatch sb) {
        if (grass || platform) {
            sb.setColor(Constants.GRASS);
            Utils.drawCenteredScaled(sb, pixel, x, y, TileMap.TILE_WIDTH, TileMap.TILE_HEIGHT + 1, toggleScale);
            sb.setColor(Constants.GRASS_SHADOW);
            Utils.drawCenteredScaled(sb, pixel, x, y - TileMap.TILE_HEIGHT / 2f - 20, TileMap.TILE_WIDTH, 40, toggleScale);
        }
        if (platform && !on && toggleTime <= 0) {
            sb.setColor(Color.WHITE);
            Utils.drawCenteredRotated(sb, platformOffImage, x - TileMap.TILE_WIDTH / 2f + platformOffImage.getRegionWidth() / 2f, y + TileMap.TILE_HEIGHT / 2f - platformOffImage.getRegionHeight() / 2f, 0);
            Utils.drawCenteredRotated(sb, platformOffImage, x - TileMap.TILE_WIDTH / 2f + platformOffImage.getRegionWidth() / 2f, y - TileMap.TILE_HEIGHT / 2f + platformOffImage.getRegionHeight() / 2f, 90);
            Utils.drawCenteredRotated(sb, platformOffImage, x + TileMap.TILE_WIDTH / 2f - platformOffImage.getRegionWidth() / 2f, y - TileMap.TILE_HEIGHT / 2f + platformOffImage.getRegionHeight() / 2f, 180);
            Utils.drawCenteredRotated(sb, platformOffImage, x + TileMap.TILE_WIDTH / 2f - platformOffImage.getRegionWidth() / 2f, y + TileMap.TILE_HEIGHT / 2f - platformOffImage.getRegionHeight() / 2f, 270);
        }
        if (platformToggle) {
            sb.setColor(Constants.TOGGLE);
            Utils.drawCentered(sb, pixel, x, y + TileMap.TILE_HEIGHT + 4, TileMap.TILE_WIDTH / 2f, TileMap.TILE_HEIGHT / 2f);
            sb.setColor(Constants.TOGGLE_SHADOW);
            Utils.drawCentered(sb, pixel, x, y + TileMap.TILE_HEIGHT + 4 - TileMap.TILE_HEIGHT / 4f, TileMap.TILE_WIDTH / 2f, 8);
            sb.setColor(Constants.TOGGLE_SHADOW);
        }
        if (strawberryToggle) Utils.drawCentered(sb, strawberryToggleImage, x, y + 110);
    }

}

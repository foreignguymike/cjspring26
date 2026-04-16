package com.distraction.cjspring26.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.util.Utils;
import com.distraction.cjspring26.entity.TileEntity;

public class Tile extends TileEntity {

    private static final float WAVE_SPEED = 100;

    public boolean water;
    public boolean grass;
    public boolean platform;
    public boolean platformToggle;
    public boolean finalToggle;

    private final TextureRegion pixel;
    private final TextureRegion platformOffImage;
    private final TextureRegion wave;

    public Tile(Context context, TileMap tileMap, int row, int col) {
        super(context, tileMap);
        setTile(row, col);
        pixel = context.getPixel();
        platformOffImage = context.getImage("platformoff");
        wave = context.getImage("wave");
    }

    public boolean canWalk() {
        return (grass || platform) && on;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (grass || platform) {
            float wavex = (WAVE_SPEED * TileMap.WAVE_TIME) % wave.getRegionWidth();
            sb.setColor(Constants.GRASS);
            Utils.drawCenteredScaled(sb, pixel, x, y, TileMap.TILE_WIDTH, TileMap.TILE_HEIGHT + 1, toggleScale);
            sb.setColor(Constants.GRASS_SHADOW);
            Utils.drawCenteredScaled(sb, pixel, x, y - TileMap.TILE_HEIGHT / 2f - 20, TileMap.TILE_WIDTH, 40, toggleScale);
            sb.setColor(Constants.WATER);
            Utils.drawCentered(sb, wave, x + wavex, y - 100);
            Utils.drawCentered(sb, wave, x + wavex - wave.getRegionWidth(), y - 100);
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
        }
        if (finalToggle) {
            sb.setColor(Constants.FINAL_TOGGLE);
            Utils.drawCentered(sb, pixel, x, y + TileMap.TILE_HEIGHT + 4, TileMap.TILE_WIDTH / 2f, TileMap.TILE_HEIGHT / 2f);
            sb.setColor(Constants.FINAL_TOGGLE_SHADOW);
            Utils.drawCentered(sb, pixel, x, y + TileMap.TILE_HEIGHT + 4 - TileMap.TILE_HEIGHT / 4f, TileMap.TILE_WIDTH / 2f, 8);
        }
    }

}

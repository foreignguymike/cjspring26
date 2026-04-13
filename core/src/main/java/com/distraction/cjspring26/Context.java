package com.distraction.cjspring26;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.audio.AudioHandler;
import com.distraction.cjspring26.screens.ScreenManager;

public class Context {

    private static final String ATLAS = "cjspring26.atlas";
    public static final String FONT64 = "fonts/fredoka-medium-64.fnt";

    public AssetManager assets;
    public AudioHandler audio;

    public ScreenManager sm;
    public SpriteBatch sb;

    private final BitmapFont font;
    private final BitmapFont dialogFont;

    public float clock;

    public Context() {
        assets = new AssetManager();
        assets.load(ATLAS, TextureAtlas.class);
        assets.load(FONT64, BitmapFont.class);
        assets.finishLoading();

        font = assets.get(FONT64, BitmapFont.class);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        dialogFont = new BitmapFont(
            new BitmapFont.BitmapFontData(font.getData().fontFile, false),
            font.getRegion(),
            font.usesIntegerPositions()
        );
        dialogFont.setColor(Constants.BLACK);

        sb = new SpriteBatch();

        audio = new AudioHandler();

        sm = new ScreenManager(new com.distraction.cjspring26.screens.PlayScreen(this));
    }

    public void incrementClock(float dt) {
        clock += dt;
    }

    public TextureRegion getImage(String key) {
        TextureRegion region = assets.get(ATLAS, TextureAtlas.class).findRegion(key);
        if (region == null) throw new IllegalArgumentException("image " + key + " not found");
        return region;
    }

    public TextureRegion getPixel() {
        return getImage("pixel");
    }

    public BitmapFont getUiFont() {
        return font;
    }

    public BitmapFont getDialogFont() {
        return dialogFont;
    }

    public void dispose() {
        sb.dispose();
        font.dispose();
    }

}

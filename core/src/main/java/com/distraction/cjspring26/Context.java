package com.distraction.cjspring26;

import com.badlogic.gdx.assets.AssetManager;
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

    public Context() {
        assets = new AssetManager();
        assets.load(ATLAS, TextureAtlas.class);
        assets.load(FONT64, BitmapFont.class);
        assets.finishLoading();

        font = assets.get(FONT64, BitmapFont.class);

        sb = new SpriteBatch();

        audio = new AudioHandler();

        sm = new ScreenManager(new com.distraction.cjspring26.screens.PlayScreen(this));
    }

    public TextureRegion getImage(String key) {
        TextureRegion region = assets.get(ATLAS, TextureAtlas.class).findRegion(key);
        if (region == null) throw new IllegalArgumentException("image " + key + " not found");
        return region;
    }

    public TextureRegion getPixel() {
        return getImage("pixel");
    }

    public BitmapFont getFont() {
        return font;
    }

    public void dispose() {
        sb.dispose();
        font.dispose();
    }

}

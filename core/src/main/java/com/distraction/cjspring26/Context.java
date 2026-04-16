package com.distraction.cjspring26;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.audio.AudioHandler;
import com.distraction.cjspring26.screens.ScreenManager;

import java.util.ArrayList;
import java.util.List;

public class Context {

    private static final String ATLAS = "cjspring26.atlas";
    public static final String FONT64 = "fonts/fredoka-medium-64.fnt";
    public static final String FONT16 = "fonts/fredoka-medium-16.fnt";

    public AssetManager assets;
    public AudioHandler audio;

    public ScreenManager sm;
    public SpriteBatch sb;

    private final BitmapFont dialogFont;
    private final BitmapFont uiFont;
    private final BitmapFont descriptionFont;

    public Context() {
        assets = new AssetManager();
        assets.load(ATLAS, TextureAtlas.class);
        assets.load(FONT64, BitmapFont.class);
        assets.load(FONT16, BitmapFont.class);
        assets.finishLoading();

        BitmapFont font64 = assets.get(FONT64, BitmapFont.class);
        font64.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        BitmapFont font16 = assets.get(FONT16, BitmapFont.class);
        font16.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        dialogFont = new BitmapFont(
            new BitmapFont.BitmapFontData(font64.getData().fontFile, false),
            font64.getRegion(),
            font64.usesIntegerPositions()
        );

        uiFont = new BitmapFont(
            new BitmapFont.BitmapFontData(font64.getData().fontFile, false),
            font64.getRegion(),
            font64.usesIntegerPositions()
        );
        uiFont.getData().setScale(0.5f);

        descriptionFont = new BitmapFont(
            new BitmapFont.BitmapFontData(font16.getData().fontFile, false),
            font16.getRegion(),
            font16.usesIntegerPositions()
        );

        sb = new SpriteBatch();

        audio = new AudioHandler();

        sm = new ScreenManager(new com.distraction.cjspring26.screens.PlayScreen(this));
//        sm = new ScreenManager(new com.distraction.cjspring26.screens.TitleScreen(this));
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
        return uiFont;
    }

    public BitmapFont getDialogFont() {
        return dialogFont;
    }

    public BitmapFont getDescriptionFont() {
        return descriptionFont;
    }

    public void dispose() {
        sb.dispose();
        uiFont.dispose();
        dialogFont.dispose();
        descriptionFont.dispose();
        assets.dispose();
        audio.dispose();
    }

}

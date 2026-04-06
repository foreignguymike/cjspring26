package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.tile.MapData;

public class Inventory extends Entity {

    private static final float INC_INTERVAL = 0.2f;

    private static final int MAX = MapData.collectibles.size();

    private final BitmapFont font;
    private String text;
    private int count;

    private float incTimer;

    public Inventory(Context context) {
        super(context);
        setImage(context.getImage("strawberry"));
        font = context.getUiFont();
        text = "0 / " + MAX;
    }

    public void increment() {
        count++;
        text = count + " / " + MAX;
        incTimer = INC_INTERVAL;
    }

    @Override
    public void update(float dt) {
        incTimer -= dt;
        if (incTimer < 0) incTimer = 0;
    }

    @Override
    public void render(SpriteBatch sb) {
        font.draw(sb, text, 140, 88);
        Utils.drawCentered(sb, image, 70, 70, 1 + incTimer * 2f);
    }
}

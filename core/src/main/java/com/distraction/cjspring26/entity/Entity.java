package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Context;

public class Entity {

    protected Context context;

    public float x;
    public float y;
    public float w;
    public float h;

    protected TextureRegion image;

    protected Entity(Context context) {
        this.context = context;
    }

    protected void setImage(TextureRegion image) {
        this.image = image;
        w = image.getRegionWidth();
        h = image.getRegionHeight();
    }

    public void update(float dt) {

    }

    public void render(SpriteBatch sb) {

    }

}

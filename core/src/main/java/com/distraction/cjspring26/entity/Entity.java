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
    protected float dx;
    protected float dy;
    protected float xdest;
    protected float ydest;

    protected TextureRegion image;

    protected Entity(Context context) {
        this.context = context;
    }

    protected void setImage(TextureRegion image) {
        this.image = image;
        w = image.getRegionWidth();
        h = image.getRegionHeight();
    }

    protected void move(float dt) {
        if (x < xdest) {
            x += dx * dt;
            if (x > xdest) x = xdest;
        } else if (x > xdest) {
            x += dx * dt;
            if (x < xdest) x = xdest;
        }
        if (y < ydest) {
            y += dy * dt;
            if (y > ydest) y = ydest;
        } else if (y > ydest) {
            y += dy * dt;
            if (y < ydest) y = ydest;
        }
    }

    protected float getRemainingDistanceM() {
        return Math.abs(x - xdest) + Math.abs(y - ydest);
    }

    protected float getRemainingDistanceE() {
        float dx = xdest - x;
        float dy = ydest - y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    protected boolean atDestination() {
        return x == xdest && y == ydest;
    }

    public void update(float dt) {

    }

    public void render(SpriteBatch sb) {

    }

}

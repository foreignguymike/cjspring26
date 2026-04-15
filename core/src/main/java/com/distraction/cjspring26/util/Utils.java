package com.distraction.cjspring26.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import java.util.List;

public class Utils {

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y) {
        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        sb.draw(image, x - w / 2, y - h / 2, w, h);
    }

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y, float w, float h) {
        sb.draw(image, x - w / 2, y - h / 2, w, h);
    }

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y, boolean flipped) {
        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        float nx = flipped ? x + w / 2 : x - w / 2;
        float nw = flipped ? -w : w;
        sb.draw(image, nx, y - h / 2, nw, h);
    }

    public static void drawCenteredScaled(SpriteBatch sb, TextureRegion image, float x, float y, float scale) {
        float w = image.getRegionWidth() * scale;
        float h = image.getRegionHeight() * scale;
        sb.draw(image, x - w / 2f, y - h / 2f, w, h);
    }

    public static void drawCenteredScaled(SpriteBatch sb, TextureRegion image, float x, float y, float w, float h, float scale) {
        w *= scale;
        h *= scale;
        sb.draw(image, x - w / 2f, y - h / 2f, w, h);
    }

    public static void drawCenteredRotated(SpriteBatch sb, TextureRegion image, float x, float y, float deg) {
        float w = image.getRegionWidth();
        float h = image.getRegionHeight();

        sb.draw(
            image,
            x - w / 2, y - h / 2,
            w / 2, h / 2,
            w, h,
            1, 1,
            deg
        );
    }

    public static void drawCenteredRotated(SpriteBatch sb, TextureRegion image, float x, float y, float w, float h, float deg) {
        sb.draw(
            image,
            x - w / 2, y - h / 2,
            w / 2, h / 2,
            w, h,
            1, 1,
            deg
        );
    }

    public static <T> void flip(T[][] map) {
        int numRows = map.length;
        for (int i = 0; i < numRows / 2; i++) {
            T[] temp = map[i];
            map[i] = map[numRows - 1 - i];
            map[numRows - 1 - i] = temp;
        }
    }

    public static String capitalize(String s) {
        s = s.toLowerCase();
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static <T> T getRandomItem(List<T> list) {
        return list.get(MathUtils.random(list.size() - 1));
    }

    public static void setAlpha(SpriteBatch sb, Color c, float a) {
        sb.setColor(c.r, c.g, c.b, a);
    }

    public static boolean anyKeysPressed(int... keys) {
        for (int key : keys) {
            if (Gdx.input.isKeyPressed(key)) return true;
        }
        return false;
    }

    public static boolean anyKeysJustPressed(int... keys) {
        for (int key : keys) {
            if (Gdx.input.isKeyJustPressed(key)) return true;
        }
        return false;
    }

}

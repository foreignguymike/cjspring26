package com.distraction.cjspring26.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Utils {

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y) {
        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        sb.draw(image, x - w / 2, y - h / 2, w, h);
    }

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y, float scale) {
        float w = image.getRegionWidth() * scale;
        float h = image.getRegionHeight() * scale;
        sb.draw(image, x - w / 2f, y - h / 2f, w, h);
    }

    public static void drawCenteredFlipped(SpriteBatch sb, TextureRegion image, float x, float y) {
        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        sb.draw(image, x + w / 2, y - h / 2, -w, h);
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

    public static <T> void flip(T[][] map) {
        int numRows = map.length;
        for (int i = 0; i < numRows / 2; i++) {
            T[] temp = map[i];
            map[i] = map[numRows - 1 - i];
            map[numRows - 1 - i] = temp;
        }
    }

}

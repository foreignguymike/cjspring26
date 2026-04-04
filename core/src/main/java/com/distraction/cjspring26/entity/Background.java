package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Constants;

public class Background extends Entity {

    private final int xcount;
    private final int ycount;

    private final Camera cam;
    private final Color color;

    public Background(Camera cam, TextureRegion image, Color color) {
        this.cam = cam;
        this.color = color;
        setImage(image);

        xcount = (int) (Constants.WIDTH / w) + 1;
        ycount = (int) (Constants.HEIGHT / h) + 1;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        int xstart = (int) (cam.position.x / w) - 1;
        int ystart = (int) (cam.position.y / h) - 1;
        for (int row = 0; row < ycount; row++) {
            for (int col = 0; col < xcount; col++) {
                sb.draw(image, (xstart + col) * w, (ystart + row) * h);
            }
        }
    }
}

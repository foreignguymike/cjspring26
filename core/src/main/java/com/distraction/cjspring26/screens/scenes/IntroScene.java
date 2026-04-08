package com.distraction.cjspring26.screens.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.screens.PlayScreen;

public class IntroScene extends Scene {

    public IntroScene(PlayScreen screen) {
        super(screen);
    }

    @Override
    public void enter() {
        screen.ignoreInput = true;
        cam.position.x = MathUtils.clamp(stuck.x, Constants.WIDTH2, tileMap.getPlayableWidth() - Constants.WIDTH2);
        cam.position.y = 1500;
        cam.update();
    }

    @Override
    public void update(float dt) {
        if (cam.position.y > stuck.y) {
            cam.position.y -= 200 * dt;
            if (cam.position.y < stuck.y) {
                cam.position.y = stuck.y;
                screen.setScene(screen.helpScene);
            }
            cam.update();
        }
    }

}

package com.distraction.cjspring26.screens.scenes;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.screens.PlayScreen;

public class PanScene extends Scene {

    private final Vector3 camTarget = new Vector3();

    public PanScene(PlayScreen screen) {
        super(screen);
    }

    @Override
    public void update(float dt) {
        camTarget.set(
            MathUtils.clamp(player.x, Constants.WIDTH2, tileMap.getTotalWidth() - Constants.WIDTH2),
            player.y,
            0
        );
        cam.position.lerp(camTarget, 2f * dt);
        cam.update();

        if (Math.abs(cam.position.x - Constants.WIDTH2) + Math.abs(cam.position.y - player.y) < 5) {
            screen.setScene(screen.playScene);
        }
    }
}

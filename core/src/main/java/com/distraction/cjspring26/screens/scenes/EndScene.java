package com.distraction.cjspring26.screens.scenes;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.screens.PlayScreen;

public class EndScene extends Scene {

    private static final float CAM_LERP = 3f;

    private final Vector3 camTarget = new Vector3();

    private int destRow = 9;
    private int destCol = 66;

    public EndScene(PlayScreen screen) {
        super(screen);
    }

    @Override
    public void enter() {
        screen.ignoreInput = true;
        player.reset();
    }

    @Override
    public void update(float dt) {
        player.up = player.row != destRow;
        player.right = player.col != destCol;

        camTarget.set(
            MathUtils.clamp(player.x, Constants.WIDTH2, tileMap.getPlayableWidth() - Constants.WIDTH2),
            player.y,
            0
        );
        cam.position.lerp(camTarget, CAM_LERP * dt);
        cam.update();
    }

    private boolean atDestination() {
        return player.row == destRow && player.col == destCol;
    }
}

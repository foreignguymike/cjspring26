package com.distraction.cjspring26.screens.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.screens.PlayScreen;
import com.distraction.cjspring26.util.Utils;

public class PlayScene extends Scene {

    private static final float CAM_LERP = 3f;

    private final Vector3 camTarget = new Vector3();

    public PlayScene(PlayScreen screen) {
        super(screen);
    }

    @Override
    public void enter() {
        screen.ignoreInput = false;
    }

    @Override
    public void input() {
        player.up = Utils.anyKeysPressed(Input.Keys.UP, Input.Keys.W);
        player.down = Utils.anyKeysPressed(Input.Keys.DOWN, Input.Keys.S);
        player.left = Utils.anyKeysPressed(Input.Keys.LEFT, Input.Keys.A);
        player.right = Utils.anyKeysPressed(Input.Keys.RIGHT, Input.Keys.D);
    }

    @Override
    public void update(float dt) {
        camTarget.set(
            MathUtils.clamp(player.x, Constants.WIDTH2, tileMap.getPlayableWidth() - Constants.WIDTH2),
            player.y,
            0
        );
        cam.position.lerp(camTarget, CAM_LERP * dt);
        cam.update();

        if (player.col == 65) {
            screen.setScene(new EndScene(screen));
        }
    }

}

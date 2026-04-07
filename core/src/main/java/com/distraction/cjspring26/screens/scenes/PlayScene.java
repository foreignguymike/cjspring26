package com.distraction.cjspring26.screens.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.screens.PlayScreen;

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
    public void exit() {

    }

    @Override
    public void input() {
        player.up = Gdx.input.isKeyPressed(Input.Keys.UP);
        player.down = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        player.left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        player.right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    }

    @Override
    public void update(float dt) {
        camTarget.set(
            MathUtils.clamp(player.x, Constants.WIDTH2, tileMap.getTotalWidth() - Constants.WIDTH2),
            player.y,
            0
        );
        cam.position.lerp(camTarget, CAM_LERP * dt);
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {

    }
}

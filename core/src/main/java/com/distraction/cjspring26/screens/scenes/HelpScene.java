package com.distraction.cjspring26.screens.scenes;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.entity.Dialog;
import com.distraction.cjspring26.screens.PlayScreen;import com.distraction.cjspring26.util.Utils;

public class HelpScene extends Scene {

    private final Vector3 camTarget = new Vector3();
    private final Dialog dialog;

    public HelpScene(PlayScreen screen) {
        super(screen);

        dialog = new Dialog(
            screen.context,
            new String[]{
                "!!",
                "Hey there!",
                "Can you help\nme please?",
                "I'm stuck!"
            },
            stuck
        );
    }

    @Override
    public void enter() {
        System.out.println("entering help scene");
        stuck.mirror = true;
        dialog.next();
    }

    @Override
    public void input() {
        if (Utils.anyKeysJustPressed(Input.Keys.ENTER, Input.Keys.SPACE)) {
            dialog.next();
        }
    }

    @Override
    public void update(float dt) {
        dialog.update(dt);
        if (dialog.isDone()) {
            tileMap.inventory.start = true;
            camTarget.set(
                MathUtils.clamp(player.x, Constants.WIDTH2, tileMap.getPlayableWidth() - Constants.WIDTH2),
                player.y,
                0
            );
            cam.position.lerp(camTarget, 2f * dt);
            cam.update();

            float cx = MathUtils.clamp(player.x, Constants.WIDTH2, tileMap.getPlayableWidth() - Constants.WIDTH2);
            if (Math.abs(cam.position.x - cx) + Math.abs(cam.position.y - player.y) < 50) {
                screen.setScene(new PlayScene(screen));
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!dialog.isDone()) dialog.render(sb);
    }
}

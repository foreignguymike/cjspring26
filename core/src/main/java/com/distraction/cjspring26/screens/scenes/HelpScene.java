package com.distraction.cjspring26.screens.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.cjspring26.entity.Dialog;
import com.distraction.cjspring26.screens.PlayScreen;

public class HelpScene extends Scene {

    public Dialog dialog;

    public HelpScene(PlayScreen screen) {
        super(screen);

        dialog = new Dialog(
            screen.context,
            new Dialog.DialogEntry[] {
                new Dialog.DialogEntry("Hey there!", stuck.x, stuck.y + 200, false),
                new Dialog.DialogEntry("Can you help\nme please?", stuck.x, stuck.y + 200, false),
                new Dialog.DialogEntry("I'm stuck!", stuck.x, stuck.y + 200, true)
            },
            500,
            150
        );
    }

    @Override
    public void enter() {
        dialog.next();
    }

    @Override
    public void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            dialog.next();
        }
    }

    @Override
    public void update(float dt) {
        dialog.update(dt);
        if (dialog.isDone()) {
            screen.setScene(screen.panScene);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        dialog.render(sb);
    }
}

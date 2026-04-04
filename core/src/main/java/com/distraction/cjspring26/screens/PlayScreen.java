package com.distraction.cjspring26.screens;

import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;

public class PlayScreen extends Screen {

    public PlayScreen(Context context) {
        super(context);

        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        ignoreInput = true;
        in.start();
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f);

        context.audio.stopMusic();
    }

    @Override
    public void input() {
        if (ignoreInput) return;
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(cam.combined);
        sb.setColor(Constants.BLUE);
        sb.draw(context.getPixel(), 0, 0, Constants.WIDTH, Constants.HEIGHT);
        context.getFont().draw(sb, "hello there", 300, 300);

        sb.draw(context.getImage("player"), 500, 500);

        in.render(sb);
        out.render(sb);

        sb.end();
    }
}

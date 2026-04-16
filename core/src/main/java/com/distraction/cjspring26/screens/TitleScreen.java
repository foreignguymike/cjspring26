package com.distraction.cjspring26.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class TitleScreen extends Screen {

    private static final Interpolation SWING_OUT = new Interpolation.SwingOut(3f);
    private static final float TITLE_TIME = 1f;
    private static final float TITLE_CYCLE = 6f;
    private static final float ENTER_TIME = 1f;

    private final TextureRegion title;
    private float time = -TITLE_TIME;

    private final BitmapFont font;

    private final TextureRegion circle;
    private final List<Vector3> clouds1 = new ArrayList<>();
    private final List<Vector3> clouds2 = new ArrayList<>();
    private float clouds1y = -400;
    private float clouds2y = -400;
    private float exitTime;
    private boolean exiting;

    public TitleScreen(Context context) {
        super(context);
        title = context.getImage("title");

        in = new Transition(context, Transition.Type.FLASH_IN, 1f, () -> ignoreInput = false);
        ignoreInput = true;
        in.start();
        out = new Transition(context, Transition.Type.FLASH_OUT, 2f, () -> {
            context.sm.replace(new PlayScreen(context));
        });

        font = context.getDialogFont();
        font.setColor(Color.WHITE);

        circle = context.getImage("circle");

        clouds1.add(new Vector3(118, 800, 0.6f));
        clouds1.add(new Vector3(300, 809, 0.4f));
        clouds1.add(new Vector3(502, 781, 0.5f));
        clouds1.add(new Vector3(742, 893, 0.6f));
        clouds1.add(new Vector3(968, 843, 0.5f));
        clouds1.add(new Vector3(1130, 848, 0.3f));
        clouds1.add(new Vector3(1441, 941, 1f));
        clouds1.add(new Vector3(1682, 885, 0.5f));
        clouds1.add(new Vector3(1897, 848, 0.4f));

        clouds2.add(new Vector3(90, 900, 0.6f));
        clouds2.add(new Vector3(405, 909, 0.7f));
        clouds2.add(new Vector3(710, 821, 0.5f));
        clouds2.add(new Vector3(979, 950, 0.6f));
        clouds2.add(new Vector3(1250, 843, 0.5f));
        clouds2.add(new Vector3(1512, 1004, 0.8f));
        clouds2.add(new Vector3(1751, 941, 0.4f));
        clouds2.add(new Vector3(2047, 885, 0.9f));
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (time > ENTER_TIME) {
                ignoreInput = true;
                exiting = true;
                context.audio.playSound("enter", 0.5f);
            }
        }
    }

    @Override
    public void update(float dt) {
        time += dt;

        in.update(dt);
        out.update(dt);

        if (exiting) exitTime += dt;

        clouds1y = -600 + 600 * (time > 0.5f ? 1f : Interpolation.fastSlow.apply(time / 0.5f));
        if (exiting) {
            clouds2y = -200 + Constants.HEIGHT * Interpolation.slowFast.apply(exitTime / 0.5f);
        } else {
            clouds2y = -700 + 500 * (time > 1f ? 1f : Interpolation.fastSlow.apply(time));
        }

        if (clouds2y > Constants.HEIGHT) {
            if (!out.started()) out.start();
        }

    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Constants.TITLE_BG);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        sb.setColor(Color.WHITE);
        if (time > 0) {
            float phase = (time % TITLE_CYCLE) / TITLE_CYCLE;
            float deg = MathUtils.sin(phase * MathUtils.PI * 8f) * MathUtils.sin(phase * MathUtils.PI * 16f) * Utils.pow(Math.max(0f, MathUtils.sin(phase * MathUtils.PI)), 6f) * 3f;
            Utils.drawCenteredRotated(sb, title, Constants.WIDTH2, Constants.HEIGHT2 + 100, deg);
        } else {
            Utils.drawCenteredScaled(sb, title, Constants.WIDTH2, Constants.HEIGHT2 + 100, SWING_OUT.apply((TITLE_TIME + time) / TITLE_TIME));
        }

        sb.setColor(Constants.CLOUDS1);
        for (Vector3 v : clouds1) Utils.drawCenteredScaled(sb, circle, v.x, Constants.HEIGHT - v.y + clouds1y, v.z);
        sb.draw(pixel, 0, 0, Constants.WIDTH, clouds1y + 200);
        sb.setColor(Constants.CLOUDS2);
        for (Vector3 v : clouds2) Utils.drawCenteredScaled(sb, circle, v.x, Constants.HEIGHT - v.y + clouds2y, v.z);
        sb.draw(pixel, 0, 0, Constants.WIDTH, clouds2y + 200);

        if (time > ENTER_TIME && time % 2 > 1) {
            font.draw(sb, "Press Enter", Constants.WIDTH2 - 165, 300);
        }

        in.render(sb);
        out.render(sb);
        sb.end();
    }

}

package com.distraction.cjspring26.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.entity.NinePatch;
import com.distraction.cjspring26.entity.player.Player;
import com.distraction.cjspring26.entity.player.PlayerRenderer;
import com.distraction.cjspring26.util.Utils;

public class CustomizeScreen extends Screen {

    private static final float POP_TIME = 0.3f;
    private static final Interpolation SWING_OUT = new Interpolation.SwingOut(2f);
    private static final Interpolation CLOSE = Interpolation.fastSlow;

    private final Player player;
    private final PlayerRenderer renderer;

    private boolean close = false;
    private float popTime;

    private final NinePatch ninePatch;
    private final TextureRegion pixel;

    public CustomizeScreen(Context context, Player player) {
        super(context);
        this.player = player;
        renderer = new PlayerRenderer(context);
        renderer.copy(player.playerRenderer);
        renderer.x = Constants.WIDTH2 - 400;
        renderer.y = Constants.HEIGHT2;

        ninePatch = new NinePatch(
            context,
            context.getImage("dialogcorner"),
            context.getImage("dialogside"),
            context.getPixel(),
            Constants.WIDTH2, Constants.HEIGHT2
        );
        ninePatch.x = Constants.WIDTH2;
        ninePatch.y = Constants.HEIGHT2;
        ninePatch.fillColor = Constants.CUSTOMIZE_BG;

        pixel = context.getPixel();
        transparent = true;
    }

    @Override
    public void input() {
        if (popTime < POP_TIME) return;
        if (Utils.anyKeysJustPressed(Input.Keys.ESCAPE, Input.Keys.TAB)) {
            close = true;
        }
    }

    @Override
    public void update(float dt) {
        if (close) {
            popTime -= dt;
            if (popTime < 0) context.sm.pop();
            ninePatch.scale = CLOSE.apply(popTime / POP_TIME);
        } else {
            if (popTime < POP_TIME) {
                popTime += dt;
                if (popTime > POP_TIME) popTime = POP_TIME;
                ninePatch.scale = SWING_OUT.apply(popTime / POP_TIME);
            }
        }
        ninePatch.update(dt);
    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(0, 0, 0, popTime * 2.5f);
        sb.draw(context.getPixel(), 0, 0, Constants.WIDTH, Constants.HEIGHT);
        ninePatch.render(sb);

        if (popTime < POP_TIME) {
            sb.end();
            return;
        }
        renderer.render(sb);
        sb.end();
    }
}

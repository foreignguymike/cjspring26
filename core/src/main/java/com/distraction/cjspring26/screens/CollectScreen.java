package com.distraction.cjspring26.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Align;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.entity.NinePatch;
import com.distraction.cjspring26.util.Customization;

public class CollectScreen extends Screen {

    private static final float POP_TIME = 0.3f;
    private static final Interpolation SWING_OUT = new Interpolation.SwingOut(2f);
    private static final Interpolation CLOSE = Interpolation.fastSlow;

    private final NinePatch ninePatch;
    private boolean close = false;
    private float popTime;

    private final BitmapFont font;
    private final GlyphLayout layout;

    public CollectScreen(Context context) {
        super(context);

        font = context.getUiFont();
        layout = new GlyphLayout();

        Customization.Custom custom = Customization.random();
        Customization.unlock(custom);
        String text = getText(custom);
        layout.setText(
            font,
            text,
            Constants.BLACK,
            Constants.WIDTH,
            Align.center,
            true
        );

        ninePatch = new NinePatch(
            context,
            context.getImage("dialogcorner"),
            context.getImage("dialogside"),
            context.getPixel(),
            layout.width + 100, layout.height + 100
        );
        ninePatch.x = Constants.WIDTH2;
        ninePatch.y = Constants.HEIGHT2;
        ninePatch.fillColor = Color.WHITE;

        transparent = true;
    }

    private static String getText(Customization.Custom custom) {
        String text = "Unlocked ";
        if (custom instanceof Customization.BodyType) text += "Body Type: " + ((Customization.BodyType) custom).name;
        else if (custom instanceof Customization.BodyColor) text += "Body Color: " + ((Customization.BodyColor) custom).name;
        else if (custom instanceof Customization.FaceType) text += "Face Type: " + ((Customization.FaceType) custom).name;
        else if (custom instanceof Customization.Accessory) text += "Accessory: " + ((Customization.Accessory) custom).name;
        return text;
    }

    @Override
    public void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (popTime >= POP_TIME) {
                close = true;
            }
        }
    }

    @Override
    public void update(float dt) {
        ninePatch.update(dt);
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
    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Color.WHITE);
        ninePatch.render(sb);
        if (popTime >= POP_TIME) {
            font.draw(sb, layout, 0, Constants.HEIGHT2 + layout.height / 2f - font.getDescent() - 5);
        }
        sb.end();
    }
}

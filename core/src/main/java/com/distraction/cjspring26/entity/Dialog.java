package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.util.Utils;
import com.sun.org.apache.bcel.internal.generic.POP;

public class Dialog extends Entity {

    private static final Interpolation SWING_OUT_5 = new Interpolation.SwingOut(3f);
    private static final Interpolation CLOSE = Interpolation.fastSlow;
    private static final float POP_TIME = 0.2f;
    private static final float CHAR_TIME = 1 / 20f;
    private static final float BLIP_TIME = 1 / 12f;

    private final Entity entity;
    private final String[] texts;
    private int textIndex = -1;
    private int charIndex = 0;
    private float time;
    private String text;

    private final BitmapFont font;
    private final GlyphLayout layout;

    public boolean lock;

    private float popTime;
    private float blipTime;

    public Dialog(Context context, String[] texts, Entity entity) {
        super(context);
        this.texts = texts;
        this.entity = entity;
        setImage(context.getImage("dialogbox"));

        font = context.getDialogFont();
        layout = new GlyphLayout();
    }

    public void next() {
        if (lock && textIndex == texts.length - 1) return;
        if (textIndex == texts.length) return;
        if (!isCurrentTextDone()) return;

        textIndex++;
        if (textIndex < texts.length) {
            text = "";
            time = CHAR_TIME;
            blipTime = 0;
            charIndex = 0;
        }
    }

    private boolean isCurrentTextDone() {
        if (textIndex < 0) return true;
        if (textIndex < texts.length) return charIndex == texts[textIndex].length();
        return true;
    }

    public boolean isTextDone() {
        int lastTextIndex = texts.length - 1;
        int lastCharIndex = texts[lastTextIndex].length() - 1;
        return textIndex == lastTextIndex && charIndex == lastCharIndex + 1;
    }

    public boolean isDone() {
        return textIndex >= texts.length && popTime <= 0;
    }

    private void updateText() {
        layout.setText(
            font,
            text,
            Constants.BLACK,
            w,
            Align.center,
            true
        );
    }

    @Override
    public void update(float dt) {
        if (textIndex >= 0 && textIndex < texts.length) {
            if (popTime < POP_TIME) {
                popTime += dt;
                if (popTime > POP_TIME) {
                    popTime = POP_TIME;
                }
            } else if (time > 0) {
                time -= dt;
                if (time <= 0) {
                    text += texts[textIndex].charAt(charIndex);
                    updateText();
                    charIndex++;
                    if (charIndex < texts[textIndex].length()) {
                        time = CHAR_TIME;
                    }
                }
                blipTime -= dt;
                if (blipTime < 0) {
                    blipTime = BLIP_TIME;
                    context.audio.playSound("dialog", 0.3f, MathUtils.random(0.92f, 1.08f));
                }
            }
        } else if (textIndex == texts.length) {
            if (popTime > 0) {
                popTime -= dt;
                if (popTime < 0) {
                    popTime = 0;
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if (textIndex == texts.length) Utils.drawCentered(sb, image, entity.x, entity.y + 220, CLOSE.apply(popTime / POP_TIME));
        else if (textIndex >= 0) Utils.drawCentered(sb, image, entity.x, entity.y + 220, SWING_OUT_5.apply(popTime / POP_TIME));
        if (textIndex >= 0 && textIndex < texts.length) font.draw(sb, layout, entity.x - w / 2, entity.y + layout.height / 2f - font.getDescent() + 206);
    }
}

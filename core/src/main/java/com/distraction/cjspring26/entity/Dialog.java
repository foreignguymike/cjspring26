package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;

public class Dialog extends Entity {

    public static class DialogEntry {
        public String text;
        public float x;
        public float y;
        public boolean end;
        public DialogEntry(String text, float x, float y, boolean end) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.end = end;
        }
    }

    private static final float CHAR_TIME = 1 / 20f;

    private final TextureRegion pixel;

    private final DialogEntry[] texts;
    private int index = -1;
    private int charIndex = 0;
    private float time;
    private String text;

    private final BitmapFont font;
    private final GlyphLayout layout;
    private float textWidth;

    public Dialog(Context context, DialogEntry[] texts, float w, float h) {
        super(context);
        this.texts = texts;
        this.w = w;
        this.h = h;

        pixel = context.getPixel();

        font = context.getDialogFont();
        layout = new GlyphLayout();
    }

    public void next() {
//        if (charIndex > 0 && charIndex < texts[index].text.length()) {
//            text = texts[index].text;
//            updateText();
//            time = 0;
//            charIndex = texts[index].text.length();
//            return;
//        }
        index++;
        text = "";
        time = CHAR_TIME;
        charIndex = 0;
    }

    public boolean isDone() {
        return index == texts.length;
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
        textWidth = layout.width;
    }

    @Override
    public void update(float dt) {
        if (index >= 0 && index < texts.length) {
            if (time > 0) {
                time -= dt;
                if (time <= 0) {
                    text += texts[index].text.charAt(charIndex);
                    updateText();
                    charIndex++;
                    if (charIndex < texts[index].text.length()) {
                        time = CHAR_TIME;
                    }
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if (index >= 0 && index < texts.length) {
            sb.draw(pixel, texts[index].x - w / 2 - 30, texts[index].y - h / 2 - 20, w + 60, h + 60);
            font.draw(sb, layout, texts[index].x - w / 2, texts[index].y + layout.height / 2f - font.getDescent());
        }
    }
}

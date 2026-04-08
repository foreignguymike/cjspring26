package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.util.Utils;

public class Dialog extends Entity {

    public static class DialogEntry {
        public String[] texts;
        public Entity entity;
        public DialogEntry(String[] texts, Entity entity) {
            this.texts = texts;
            this.entity = entity;
        }
    }

    private static final float CHAR_TIME = 1 / 20f;

    private final DialogEntry[] entries;
    private int entryIndex = -1;
    private int textIndex = 0;
    private int charIndex = 0;
    private float time;
    private String text;

    private final BitmapFont font;
    private final GlyphLayout layout;

    public boolean lock;

    public Dialog(Context context, DialogEntry[] entries) {
        super(context);
        this.entries = entries;
        setImage(context.getImage("dialogbox"));

        font = context.getDialogFont();
        layout = new GlyphLayout();
    }

    public void next() {
        if (entryIndex ==  entries.length) return;
        if (lock && entryIndex == entries.length - 1 && textIndex == entries[entryIndex].texts.length - 1) return;
        if (entryIndex < 0) {
            entryIndex = 0;
            textIndex = -1;
            charIndex = 0;
        }

        if (!isCurrentTextDone()) return;

        textIndex++;
        if (textIndex >= entries[entryIndex].texts.length) {
            entryIndex++;
        } else {
            text = "";
            time = CHAR_TIME;
            charIndex = 0;
        }
    }

    // optional press to skip to show all text
    private void skip() {
        if (charIndex > 0 && charIndex < entries[entryIndex].texts[textIndex].length()) {
            text = entries[entryIndex].texts[textIndex];
            updateText();
            time = 0;
            charIndex = entries[entryIndex].texts[textIndex].length();
        }
    }

    private boolean isCurrentTextDone() {
        if (entryIndex >= 0 && entryIndex < entries.length) {
            if (textIndex >= 0 && textIndex < entries[entryIndex].texts.length) {
                return charIndex == entries[entryIndex].texts[textIndex].length();
            }
        }
        return true;
    }

    public boolean isTextDone() {
        int lastEntryIndex = entries.length - 1;
        int lastTextIndex = entries[lastEntryIndex].texts.length - 1;
        int lastCharIndex = entries[lastEntryIndex].texts[lastTextIndex].length() - 1;
        return entryIndex == lastEntryIndex
            && textIndex == lastTextIndex
            && charIndex == lastCharIndex + 1;
    }

    public boolean isDone() {
        return entryIndex >= entries.length;
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
        if (entryIndex >= 0 && entryIndex < entries.length) {
            if (textIndex >= 0 && textIndex < entries[entryIndex].texts.length) {
                if (time > 0) {
                    time -= dt;
                    if (time <= 0) {
                        text += entries[entryIndex].texts[textIndex].charAt(charIndex);
                        updateText();
                        charIndex++;
                        if (charIndex < entries[entryIndex].texts[textIndex].length()) {
                            time = CHAR_TIME;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if (entryIndex >= 0 && entryIndex < entries.length) {
            Utils.drawCentered(sb, image, entries[entryIndex].entity.x, entries[entryIndex].entity.y + 220);
            font.draw(sb, layout, entries[entryIndex].entity.x - w / 2, entries[entryIndex].entity.y + layout.height / 2f - font.getDescent() + 206);
        }
    }
}

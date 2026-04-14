package com.distraction.cjspring26.entity.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.entity.Entity;
import com.distraction.cjspring26.util.Utils;

public class PlayerRenderer extends Entity {

    public enum BodyType {
        DEFAULT(0.8f, -40, 12),
        BLOCK(0.8f, -44, 16)
        ;
        public final float a;
        public final float shadowy;
        public final float highlighty;

        BodyType(float a, float shadowy, float highlighty) {
            this.a = a;
            this.shadowy = shadowy;
            this.highlighty = highlighty;
        }
    }

    public enum BodyColor {
        WHITE(9, 8),
        BLACK(2, 1),
        RED(15, 14),
        GREEN(32, 31),
        BLUE(48, 47),
        PINK(57, 56),
        PEACH(63, 62),
        CYAN(43, 42),
        VIOLET(53, 52),
        ORANGE(23, 22),
        YELLOW(28, 27)
        ;
        public final Color color;
        public final Color shadow;

        BodyColor(int index1, int index2) {
            this.color = Constants.RESURRECT_64[index1];
            this.shadow = Constants.RESURRECT_64[index2];
        }
    }

    public enum FaceType {
        DEFAULTM(0),
        DEFAULTF(1),
        SLEEP(2),
        COOL(3),
        CRAZY(4),
        DOG(5),
        CAT(6)
        ;
        public final int index;

        FaceType(int index) {
            this.index = index;
        }
    }

    public BodyType bodyType;
    public BodyColor bodyColor;
    public FaceType faceType;

    private TextureRegion outline;
    private TextureRegion fill;
    private TextureRegion shadow;
    private TextureRegion highlight;

    private TextureRegion face;

    public PlayerRenderer(Context context) {
        super(context);

        w = h = 128;
    }

    public void copy(PlayerRenderer other) {
        setBody(other.bodyType, other.bodyColor);
        setFaceType(other.faceType);
    }

    public void randomize() {
        setBody(Utils.randomEnum(BodyType.class), Utils.randomEnum(BodyColor.class));
        setFaceType(Utils.randomEnum(FaceType.class));
    }

    public void setBody(BodyType bodyType, BodyColor bodyColor) {
        this.bodyType = bodyType;
        this.bodyColor = bodyColor;
        if (bodyType == BodyType.DEFAULT) {
            outline = context.getImage("playeroutline");
            fill = context.getImage("playerfill");
            shadow = context.getImage("playershadow");
            highlight = context.getImage("playerhighlight");
        } else if (bodyType == BodyType.BLOCK) {
            outline = context.getImage("playeroutlineblock");
            fill = context.getImage("playerfillblock");
            shadow = context.getImage("playershadowblock");
            highlight = context.getImage("playerhighlightblock");
        }
    }

    public void setFaceType(FaceType faceType) {
        this.faceType = faceType;
        face = context.getImage("face" + faceType.index);
    }

    public void prepare(float x, float y, boolean moving, boolean mirror) {
        this.x = x;
        this.y = y;
        this.moving = moving;
        this.mirror = mirror;
    }

    @Override
    public void render(SpriteBatch sb) {
        float h = this.h;

        // body
        if (bodyType != null && bodyColor != null) {
            Utils.setAlpha(sb, bodyColor.color, bodyType.a);
            Utils.drawCentered(sb, fill, x, y, w, h);
            Utils.setAlpha(sb, bodyColor.shadow, bodyType.a);
            Utils.drawCentered(sb, shadow, x, y + bodyType.shadowy);
        }
        sb.setColor(Color.WHITE);
        if (highlight != null) {
            Utils.drawCentered(sb, highlight, x, y + bodyType.highlighty);
        }
        if (outline != null) {
            Utils.drawCentered(sb, outline, x, y, w, h);
        }

        // face
        if (face != null) {
            Utils.drawCentered(sb, face, x + 10 * (mirror ? -1 : 1), y - 28 + (moving ? 15 : 0), mirror);
        }
    }

}

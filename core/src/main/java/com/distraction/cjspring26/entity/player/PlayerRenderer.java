package com.distraction.cjspring26.entity.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.entity.Entity;
import com.distraction.cjspring26.util.Customization;
import com.distraction.cjspring26.util.Utils;

public class PlayerRenderer extends Entity {

    public Customization.BodyType bodyType;
    public Customization.BodyColor bodyColor;
    public Customization.FaceType faceType;
    public Customization.Accessory acc1;
    public Customization.Accessory acc2;
    public Customization.Accessory acc3;

    private TextureRegion outline;
    private TextureRegion fill;
    private TextureRegion shadow;
    private TextureRegion highlight;
    private TextureRegion face;
    private TextureRegion acc1Image;
    private TextureRegion acc2Image;
    private TextureRegion acc3Image;

    public PlayerRenderer(Context context) {
        super(context);

        w = h = 128;
    }

    public void copy(PlayerRenderer other) {
        setBody(other.bodyType, other.bodyColor);
        setFaceType(other.faceType);
    }

    public void randomize() {
        setBody(Utils.getRandomItem(Customization.unlockedBodyTypes), Utils.getRandomItem(Customization.unlockedBodyColors));
        setFaceType(Utils.getRandomItem(Customization.unlockedFaceTypes));
    }

    public void setBody(Customization.BodyType bodyType, Customization.BodyColor bodyColor) {
        this.bodyType = bodyType;
        this.bodyColor = bodyColor;
        if (bodyType == Customization.BodyType.DEFAULT) {
            outline = context.getImage("playeroutline");
            fill = context.getImage("playerfill");
            shadow = context.getImage("playershadow");
            highlight = context.getImage("playerhighlight");
        } else if (bodyType == Customization.BodyType.BLOCK) {
            outline = context.getImage("playeroutlineblock");
            fill = context.getImage("playerfillblock");
            shadow = context.getImage("playershadowblock");
            highlight = context.getImage("playerhighlightblock");
        }
    }

    public void setFaceType(Customization.FaceType faceType) {
        this.faceType = faceType;
        face = context.getImage("face" + faceType.index);
    }

    public void setAcc1(Customization.Accessory acc1) {
        this.acc1 = acc1;
    }

    public void setAcc2(Customization.Accessory acc2) {
        this.acc2 = acc2;
    }

    public void setAcc3(Customization.Accessory acc3) {
        this.acc3 = acc3;
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

        // acc background
        sb.setColor(Color.WHITE);
        if (acc1Image != null) {

        }
        if (acc2Image != null) {

        }
        if (acc3Image != null) {

        }

        // body
        if (bodyType != null && bodyColor != null) {
            Utils.setAlpha(sb, bodyColor.color, bodyType.a);
            Utils.drawCentered(sb, fill, x, y, w, h);
            Utils.setAlpha(sb, Constants.BLACK, 0.15f);
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

        // acc foreground
    }

}

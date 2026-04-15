package com.distraction.cjspring26.entity.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.entity.Entity;
import com.distraction.cjspring26.util.Customization;
import com.distraction.cjspring26.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayerRenderer extends Entity {

    private static class OrderData {
        public final Customization.Accessory acc;
        public final TextureRegion image;
        public OrderData(Customization.Accessory acc, TextureRegion image) {
            this.acc = acc;
            this.image = image;
        }
    }

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

    private List<OrderData> order = new ArrayList<>();

    public PlayerRenderer(Context context) {
        super(context);

        w = h = 128;
    }

    public void copy(PlayerRenderer other) {
        setBody(other.bodyType, other.bodyColor);
        setFaceType(other.faceType);
        setAcc1(other.acc1);
        setAcc2(other.acc2);
        setAcc3(other.acc3);
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
        if (acc1 != null) {
            acc1Image = context.getImage("acc" + acc1.index);
        }
        updateOrder();
    }

    public void setAcc2(Customization.Accessory acc2) {
        this.acc2 = acc2;
        if (acc2 != null) {
            acc2Image = context.getImage("acc" + acc2.index);
        }
        updateOrder();
    }

    public void setAcc3(Customization.Accessory acc3) {
        this.acc3 = acc3;
        if (acc3 != null) {
            acc3Image = context.getImage("acc" + acc3.index);
        }
        updateOrder();
    }

    public void prepare(float x, float y, boolean moving, boolean mirror) {
        this.x = x;
        this.y = y;
        this.moving = moving;
        this.mirror = mirror;
    }

    private void updateOrder() {
        order.clear();
        if (acc1 != null) order.add(new OrderData(acc1, acc1Image));
        if (acc2 != null) order.add(new OrderData(acc2, acc2Image));
        if (acc3 != null) order.add(new OrderData(acc3, acc3Image));
        order.sort(Comparator.comparingInt(it -> it.acc.ordinal()));
    }

    @Override
    public void render(SpriteBatch sb) {
        float h = this.h;

        // acc background
        for (OrderData o : order) renderAccBackground(sb, o.acc, o.image);

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
        for (OrderData o : order) renderAcc(sb, o.acc, o.image);
    }

    private void renderAccBackground(SpriteBatch sb, Customization.Accessory acc, TextureRegion image) {
        if (acc == null || image == null) return;
        sb.setColor(Color.WHITE);
        if (acc == Customization.Accessory.HALO) {
            int w = image.getRegionWidth();
            int h = image.getRegionHeight();
            sb.draw(image.getTexture(), x - w / 2f + acc.x, y + acc.y, w, h / 2f, image.getRegionX(), image.getRegionY(), w, h / 2, false, false);
        }
    }

    private void renderAcc(SpriteBatch sb, Customization.Accessory acc, TextureRegion image) {
        if (acc == null || image == null) return;
        if (acc == Customization.Accessory.BUBBLE) sb.setColor(bodyColor.color);
        else sb.setColor(Color.WHITE);
        if (acc == Customization.Accessory.HALO) {
            int w = image.getRegionWidth();
            int h = image.getRegionHeight();
            sb.draw(image.getTexture(), x - w / 2f + acc.x, y - h / 2f + acc.y, w, h / 2f, image.getRegionX(), image.getRegionY() + h / 2, w, h / 2, false, false);
        } else {
            Utils.drawCentered(sb, image, x + acc.x * (mirror ? -1 : 1), y + acc.y, mirror);
        }
    }

}

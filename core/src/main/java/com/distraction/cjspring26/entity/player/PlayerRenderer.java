package com.distraction.cjspring26.entity.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.entity.Entity;
import com.distraction.cjspring26.util.Customization;
import com.distraction.cjspring26.util.Utils;

import java.util.ArrayList;
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

    private final List<OrderData> order = new ArrayList<>();
    private float time;
    private float wingTime;

    public PlayerRenderer(Context context) {
        super(context);

        w = h = 128;
    }

    public void copy(PlayerRenderer other) {
        setBody(other.bodyType);
        setBodyColor(other.bodyColor);
        setFaceType(other.faceType);
        setAcc1(other.acc1);
        setAcc2(other.acc2);
        setAcc3(other.acc3);
    }

    public void randomize() {
        setBody(Utils.getRandomItem(Customization.unlockedBodyTypes));
        setBodyColor(Utils.getRandomItem(Customization.unlockedBodyColors));
        setFaceType(Utils.getRandomItem(Customization.unlockedFaceTypes));
        if (!Customization.unlockedAccessories.isEmpty()) {
            setAcc1(Utils.getRandomItem(Customization.unlockedAccessories));
        }
    }

    public void setBody(Customization.BodyType bodyType) {
        this.bodyType = bodyType;
        String bodyFileName = bodyType == Customization.BodyType.MATTE ? "" : bodyType.fileName;
        String fillFileName = bodyType == Customization.BodyType.MATTE ? "" : bodyType.fileName;
        outline = context.getImage("playeroutline" + bodyFileName);
        fill = context.getImage("playerfill" + fillFileName);
        shadow = context.getImage("playershadow" + bodyType.fileName);
        highlight = context.getImage("playerhighlight" + bodyType.fileName);
    }

    public void setBodyColor(Customization.BodyColor bodyColor) {
        this.bodyColor = bodyColor;
    }

    public void setFaceType(Customization.FaceType faceType) {
        this.faceType = faceType;
        face = context.getImage("face" + faceType.fileName);
    }

    public void setAcc1(Customization.Accessory acc1) {
        this.acc1 = acc1;
        if (acc1 != null) {
            acc1Image = context.getImage("acc" + acc1.fileName);
        }
        updateOrder();
    }

    public void setAcc2(Customization.Accessory acc2) {
        this.acc2 = acc2;
        if (acc2 != null) {
            acc2Image = context.getImage("acc" + acc2.fileName);
        }
        updateOrder();
    }

    public void setAcc3(Customization.Accessory acc3) {
        this.acc3 = acc3;
        if (acc3 != null) {
            acc3Image = context.getImage("acc" + acc3.fileName);
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
    public void update(float dt) {
        time += dt;
        wingTime += dt * (moving ? 20 : 1);
    }

    @Override
    public void render(SpriteBatch sb) {
        float h = this.h;

        // acc background
        for (int i = order.size() - 1; i >= 0; i--) {
            OrderData o = order.get(i);
            renderAccBackground(sb, o.acc, o.image);
        }

        // body
        if (bodyType != null && bodyColor != null) {
            Utils.setAlpha(sb, bodyColor.color, bodyType.a);
            Utils.drawCentered(sb, fill, x, y, w, h);
            Utils.setAlpha(sb, Constants.BLACK, 0.15f);
            Utils.drawCentered(sb, shadow, x, y);
        }
        if (highlight != null) {
            Utils.setAlpha(sb, Color.WHITE, 0.3f);
            Utils.drawCentered(sb, highlight, x, y);
        }
        sb.setColor(Color.WHITE);
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
        } else if (acc == Customization.Accessory.HORNS) {
            Utils.drawCentered(sb, image, x - acc.x * (mirror ? -1f : 1f), y + acc.y, !mirror);
        } else if (acc == Customization.Accessory.ANGEL_WINGS || acc == Customization.Accessory.DEMON_WINGS) {
            Utils.drawCenteredRotated(sb, image, x - acc.x * (mirror ? -0.84f : 0.84f), y + 4, -10 * (1 - MathUtils.sin(wingTime * 1.5f)), 0.8f, 0.5f, !mirror);
        } else if (acc == Customization.Accessory.FLOWER || acc == Customization.Accessory.RIBBON) {
            if (mirror) Utils.drawCentered(sb, image, x + acc.x * 0.8f, y + acc.y, true);
        } else if (acc == Customization.Accessory.STAR) {
            float dy = MathUtils.sin(2 * time);
            if (dy > 0) {
                float dx = MathUtils.cos(2 * time);
                Utils.drawCenteredRotated(sb, image, x + dx * 100, y - 20 + dy * 10, 30 * time);
            }
        } else if (acc == Customization.Accessory.BEE) {
            float dy = MathUtils.sin(2 * time);
            if (dy > 0) {
                float dx = MathUtils.cos(2 * time);
                Utils.drawCentered(sb, image, x + dx * 100, y - 20 + dy * 10 + 10 * MathUtils.sin(6 * time), true);
            }
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
        } else if (acc == Customization.Accessory.HORNS) {
            Utils.drawCentered(sb, image, x + acc.x * (mirror ? -1f : 1f), y + acc.y, mirror);
        } else if (acc == Customization.Accessory.ANGEL_WINGS || acc == Customization.Accessory.DEMON_WINGS) {
            Utils.drawCenteredRotated(sb, image, x + acc.x * (mirror ? -1 : 1), y + acc.y, 10 * MathUtils.sin(wingTime * 1.5f) - 10, 0.8f, 0.5f, mirror);
        } else if (acc == Customization.Accessory.FLOWER || acc == Customization.Accessory.RIBBON) {
            if (!mirror) Utils.drawCentered(sb, image, x + acc.x, y + acc.y);
        } else if (acc == Customization.Accessory.STAR) {
            float dy = MathUtils.sin(2 * time);
            if (dy < 0) {
                float dx = MathUtils.cos(2 * time);
                Utils.drawCenteredRotated(sb, image, x + dx * 100, y - 20 + dy * 10, 30 * time);
            }
        } else if (acc == Customization.Accessory.BEE) {
            float dy = MathUtils.sin(2 * time);
            if (dy < 0) {
                float dx = MathUtils.cos(2 * time);
                Utils.drawCentered(sb, image, x + dx * 100, y - 20 + dy * 10 + 10 * MathUtils.sin(6 * time));
            }
        } else if (acc == Customization.Accessory.BLUSH) {
            Utils.drawCentered(sb, image, x + acc.x * (mirror ? -1 : 1), y + acc.y + (moving ? 15 : 0), mirror);
        } else {
            Utils.drawCentered(sb, image, x + acc.x * (mirror ? -1 : 1), y + acc.y, mirror);
        }
    }

}

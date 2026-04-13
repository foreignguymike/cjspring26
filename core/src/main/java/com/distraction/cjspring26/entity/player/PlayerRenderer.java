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
        DEFAULT
        ;

    }

    public enum BodyColor {
        RED(15, 14),
        GREEN(32, 31),
        BLUE(48, 47),
        PINK(57, 56)
        ;
        public Color color;
        public Color shadow;

        BodyColor(int index1, int index2) {
            this.color = Constants.RESURRECT_64[index1];
            this.shadow = Constants.RESURRECT_64[index2];
        }
    }

    public enum FaceType {
        DEFAULT(0)
        ;
        public int index;

        FaceType(int index) {
            this.index = index;
        }
    }

    private final Player player;

    public BodyType bodyType = BodyType.DEFAULT;
    public BodyColor bodyColor = BodyColor.PINK;
    public FaceType faceType = FaceType.DEFAULT;

    private TextureRegion outline;
    private TextureRegion fill;
    private TextureRegion shadow;
    private TextureRegion highlight;

    private TextureRegion face;

    public PlayerRenderer(Context context, Player player) {
        super(context);
        this.player = player;

        setBodyType(BodyType.DEFAULT);
        setFaceType(FaceType.DEFAULT);

        w = h = 128;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
        if (bodyType == BodyType.DEFAULT) {
            outline = context.getImage("playeroutline");
            fill = context.getImage("playerfill");
            shadow = context.getImage("playershadow");
            highlight = context.getImage("playerhighlight");
        }
    }

    public void setFaceType(FaceType faceType) {
        this.faceType = faceType;
        face = context.getImage("face" + faceType.index);
    }

    @Override
    public void render(SpriteBatch sb) {
        float x = player.x;
        float y = player.y + player.jumpy + Player.OFFSET_Y;
        float h = this.h;

        // body
        sb.setColor(bodyColor.color);
        Utils.drawCentered(sb, fill, x, y, w, h);
        sb.setColor(bodyColor.shadow);
        Utils.drawCentered(sb, shadow, x, y - 40);
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, highlight, x, y + 12);
        Utils.drawCentered(sb, outline, x, y, w, h);

        // face
        Utils.drawCentered(sb, face, x + 10 * (player.mirror ? -1 : 1), y - 28 + (player.moving ? 15 : 0), player.mirror);
    }

}

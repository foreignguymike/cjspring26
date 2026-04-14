package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.util.Utils;

public class NinePatch extends Entity {

    public Color fillColor = Color.WHITE;

    private final TextureRegion corner;
    private final TextureRegion side;
    private final TextureRegion fill;

    public NinePatch(Context context, TextureRegion corner, TextureRegion side, TextureRegion fill, float w, float h) {
        super(context);
        this.corner = corner;
        this.side = side;
        this.fill = fill;
        this.w = w;
        this.h = h;
    }

    @Override
    public void render(SpriteBatch sb) {
        float w = this.w * scale;
        float h = this.h * scale;
        float right = w / 2f + corner.getRegionWidth() / 2f;
        float up = h / 2f + corner.getRegionHeight() / 2f;

        sb.setColor(fillColor);
        Utils.drawCentered(sb, fill, x, y, w + corner.getRegionWidth() + 2, h + corner.getRegionHeight() + 2); // fill

        sb.setColor(Color.WHITE);
        Utils.drawCenteredRotated(sb, corner, x - right, y + up, 0); // tl
        Utils.drawCenteredRotated(sb, corner, x - right, y - up, 90); // bl
        Utils.drawCenteredRotated(sb, corner, x + right, y - up, 180); // br
        Utils.drawCenteredRotated(sb, corner, x + right, y + up, -90); // tr

        Utils.drawCenteredRotated(sb, side, x, y + up, w, side.getRegionHeight(), 0); // up
        Utils.drawCenteredRotated(sb, side, x, y - up, w, side.getRegionHeight(), 180); // down
        Utils.drawCenteredRotated(sb, side, x - right, y, h, side.getRegionHeight(), 90);   // left
        Utils.drawCenteredRotated(sb, side, x + right, y, h, side.getRegionHeight(), -90);  // right
    }
}

package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.tile.TileMap;

public class Stone extends TileEntity {

    public Stone(Context context, TileMap tileMap, int row, int col, boolean on) {
        super(tileMap);
        setImage(context.getImage("stone"));
        setTile(row, col);
        this.on = on;
    }

    @Override
    public void render(SpriteBatch sb) {
        int yoffset = getYOffset();
        sb.setColor(Color.WHITE);
        if (!on) sb.setColor(Constants.BLUE);
        Utils.drawCentered(sb, image, x, y + yoffset);
        sb.setColor(Color.WHITE);
    }
}

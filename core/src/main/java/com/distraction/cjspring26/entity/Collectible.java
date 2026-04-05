package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.tile.TileMap;

public class Collectible extends TileEntity {

    private float time;

    public Collectible(Context context, TileMap tileMap, int row, int col) {
        super(tileMap);

        setImage(context.getImage("ladderinv"));
        setTile(row, col);
    }

    @Override
    public void update(float dt) {
        time += dt;
    }

    @Override
    public void render(SpriteBatch sb) {
        Utils.drawCentered(sb, image, x, y + 10 * MathUtils.sin(time * 4));
    }
}

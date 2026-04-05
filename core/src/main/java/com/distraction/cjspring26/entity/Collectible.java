package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.tile.TileMap;

public class Collectible extends TileEntity {

    public enum Type {
        LADDER("ladderinv")
        ;
        public String name;
        Type(String name) {
            this.name = name;
        }
    }

    public final Type type;
    private float time;

    public Collectible(Context context, TileMap tileMap, Type type, int row, int col) {
        super(tileMap);
        this.type = type;

        setImage(context.getImage(type.name));
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

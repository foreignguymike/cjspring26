package com.distraction.cjspring26.entity;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.tile.TileMap;

public abstract class TileEntity extends Entity {

    private static final float TOTAL_TOGGLE_TIME = 0.1f;
    private static final Interpolation SWING_OUT_5 = new Interpolation.SwingOut(3f);

    protected TileMap tileMap;
    public int row, col;
    public boolean on = true;
    protected float toggleTime;
    protected float toggleScale;

    protected TileEntity(Context context, TileMap tileMap) {
        super(context);
        this.tileMap = tileMap;
    }

    public void toggle() {
        on = !on;
        toggleTime = TOTAL_TOGGLE_TIME;
    }

    public void setTile(int row, int col) {
        this.row = row;
        this.col = col;
        x = tileMap.coord(col);
        y = tileMap.coord(row);
        xdest = x;
        ydest = y;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        toggleTime -= dt;
        if (toggleTime < 0) toggleTime = 0;
        if (on) toggleScale = SWING_OUT_5.apply(1 - toggleTime / TOTAL_TOGGLE_TIME);
        else toggleScale = MathUtils.map(TOTAL_TOGGLE_TIME, 0, 1, 0, toggleTime);
    }

}

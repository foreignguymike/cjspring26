package com.distraction.cjspring26.entity;

import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.tile.TileMap;

public abstract class TileEntity extends Entity {

    protected TileMap tileMap;
    public int row, col;
    public boolean on = true;
    protected float toggleTime;
    protected float toggleScale;
    protected final float totalToggleTime = 0.1f;

    protected TileEntity(Context context, TileMap tileMap) {
        super(context);
        this.tileMap = tileMap;
    }

    public void toggle() {
        on = !on;
        toggleTime = totalToggleTime;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        toggleTime -= dt;
        if (toggleTime < 0) toggleTime = 0;

        if (on) toggleScale = MathUtils.map(totalToggleTime, 0, 0, 1, toggleTime);
        else toggleScale = MathUtils.map(totalToggleTime, 0, 1, 0, toggleTime);
    }

    protected void setTile(int row, int col) {
        this.row = row;
        this.col = col;
        this.x = tileMap.coord(col);
        this.y = tileMap.coord(row);
    }

}

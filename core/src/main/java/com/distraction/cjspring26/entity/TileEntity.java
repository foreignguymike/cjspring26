package com.distraction.cjspring26.entity;

import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.tile.TileMap;

public abstract class TileEntity extends Entity {

    protected TileMap tileMap;
    public int row, col;
    protected boolean on = true;
    protected float toggleTime;
    protected float yoffset;
    protected final float totalToggleTime = 0.2f;
    protected final float maxyoffset = 200;

    protected TileEntity(TileMap tileMap) {
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

        if (on) yoffset = MathUtils.map(totalToggleTime, 0, -200, 0, toggleTime);
        else yoffset = MathUtils.map(totalToggleTime, 0, 0, -200, toggleTime);
    }

    protected void setTile(int row, int col) {
        this.row = row;
        this.col = col;
        this.x = tileMap.coord(col);
        this.y = tileMap.coord(row);
    }

}

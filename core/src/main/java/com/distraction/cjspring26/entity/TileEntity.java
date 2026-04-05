package com.distraction.cjspring26.entity;

import com.distraction.cjspring26.tile.TileMap;

public abstract class TileEntity extends Entity {

    protected TileMap tileMap;
    public int row, col;
    protected boolean on = true;

    protected TileEntity(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public void toggle() {
        on = !on;
    }

    protected int getYOffset() {
        return on ? 0 : -120;
    }

    protected void setTile(int row, int col) {
        this.row = row;
        this.col = col;
        this.x = tileMap.coord(col);
        this.y = tileMap.coord(row);
    }

}

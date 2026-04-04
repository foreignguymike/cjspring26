package com.distraction.cjspring26.entity;

import com.distraction.cjspring26.tile.TileMap;

public abstract class TileEntity extends Entity {

    protected TileMap tileMap;
    public int row, col;

    protected TileEntity(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    protected void setTile(int row, int col) {
        this.row = row;
        this.col = col;
        this.x = tileMap.coord(col);
        this.y = tileMap.coord(row);
    }

}

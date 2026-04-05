package com.distraction.cjspring26.tile;

public class Tile {

    public boolean water;
    public boolean grass;
    public boolean bridge;
    public boolean stone;
    public boolean stoneOn;
    public boolean anchor;

    public boolean canBuildBridge() {
        return water && !grass && !bridge && !stoneOn;
    }

    public boolean canWalk() {
        return grass || bridge || stoneOn;
    }

}

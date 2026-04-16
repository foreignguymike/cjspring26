package com.distraction.cjspring26;

public enum Direction {
    UP(90f),
    DOWN(-90f),
    LEFT(180f),
    RIGHT(0f);

    public final float deg;

    Direction(float deg) {
        this.deg = deg;
    }
}

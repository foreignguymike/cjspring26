package com.distraction.cjspring26;

import com.badlogic.gdx.graphics.Color;

public class Constants {

    public static final String TITLE = "Long Way Home";

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final int WIDTH2 = WIDTH / 2;

    public static final int SWIDTH = WIDTH;
    public static final int SHEIGHT = HEIGHT;

    public static final boolean FULLSCREEN = true;

    public static final Color[] RESURRECT_64 = new Color[] {
        // 0 unsaturated brown
        Color.valueOf("2e222f"), Color.valueOf("3e3546"), Color.valueOf("625565"), Color.valueOf("966c6c"), Color.valueOf("ab947a"),
        // 5 unsaturated blue
        Color.valueOf("694f62"), Color.valueOf("7f708a"), Color.valueOf("9babb2"), Color.valueOf("c7dcd0"), Color.valueOf("ffffff"),
        // 10 dark red
        Color.valueOf("6e2727"), Color.valueOf("b33831"), Color.valueOf("ea4f36"), Color.valueOf("f57d4a"),
        // 14 red-orange
        Color.valueOf("ae2334"), Color.valueOf("e83b3b"), Color.valueOf("fb6b1d"), Color.valueOf("f79617"), Color.valueOf("f9c22b"),
        // 19 orange
        Color.valueOf("7a3045"), Color.valueOf("9e4539"), Color.valueOf("cd683d"), Color.valueOf("e6904e"), Color.valueOf("fbb954"),
        // 24 olive
        Color.valueOf("4c3e24"), Color.valueOf("676633"), Color.valueOf("a2a947"), Color.valueOf("d5e04b"), Color.valueOf("fbff86"),
        // 29 green
        Color.valueOf("165a4c"), Color.valueOf("239063"), Color.valueOf("1ebc73"), Color.valueOf("91db69"), Color.valueOf("cddf6c"),
        // 34 poison green
        Color.valueOf("313638"), Color.valueOf("374e4a"), Color.valueOf("547e64"), Color.valueOf("92a984"), Color.valueOf("b2ba90"),
        // 39 teal
        Color.valueOf("0b5e65"), Color.valueOf("0b8a8f"), Color.valueOf("0eaf9b"), Color.valueOf("30e1b9"), Color.valueOf("8ff8e2"),
        // 44 blue
        Color.valueOf("323353"), Color.valueOf("484a77"), Color.valueOf("4d65b4"), Color.valueOf("4d9be6"), Color.valueOf("8fd3ff"),
        // 49 violet
        Color.valueOf("45293f"), Color.valueOf("6b3e75"), Color.valueOf("905ea9"), Color.valueOf("a884f3"), Color.valueOf("eaaded"),
        // 54 pink
        Color.valueOf("753c54"), Color.valueOf("a24b6f"), Color.valueOf("cf657f"), Color.valueOf("ed8099"),
        // 58 pink-peach
        Color.valueOf("831c5d"), Color.valueOf("c32454"), Color.valueOf("f04f78"), Color.valueOf("f68181"), Color.valueOf("fca790"), Color.valueOf("fdcbb0")
    };

    public static final Color BLACK = RESURRECT_64[0];
    public static final Color WATER = RESURRECT_64[48];

    public static final Color GRASS = RESURRECT_64[31];
    public static final Color GRASS_SHADOW = RESURRECT_64[30];
    public static final Color TOGGLE = RESURRECT_64[28];
    public static final Color TOGGLE_SHADOW = RESURRECT_64[26];
    public static final Color FINAL_TOGGLE = RESURRECT_64[57];
    public static final Color FINAL_TOGGLE_SHADOW = RESURRECT_64[56];

    public static final Color TEXTURE_OPACITY = new Color(1, 1, 1, 0.1f);

}

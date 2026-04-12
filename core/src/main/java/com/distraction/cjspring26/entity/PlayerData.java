package com.distraction.cjspring26.entity;

public class PlayerData {

    private PlayerData() {}

    public enum Face {
        DEFAULT(1, 8, 25)
        ;
        public final String nameOutline;
        public final String nameFill;
        public final float x;
        public final float y;

        Face(int index, float x, float y) {
            nameOutline = "faceo" + index;
            nameFill = "facef" + index;
            this.x = x;
            this.y = y;
        }
    }

    public enum Hair {
        ANIME(1, 8, 47)
        ;
        public final String nameOutline;
        public final String nameFill;
        public final float x;
        public final float y;

        Hair(int index, float x, float y) {
            nameOutline = "hairo" + index;
            nameFill = "hairf" + index;
            this.x = x;
            this.y = y;
        }
    }

}

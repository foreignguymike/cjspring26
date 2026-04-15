package com.distraction.cjspring26.util;

import com.badlogic.gdx.graphics.Color;
import com.distraction.cjspring26.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Customization {

    public interface Custom {}

    public enum BodyType implements Custom {
        DEFAULT("Slime", 0.8f, -40, 12),
        BLOCK("Block", 0.8f, -44, 16)
        ;
        public final String name;
        public final float a;
        public final float shadowy;
        public final float highlighty;

        BodyType(String name, float a, float shadowy, float highlighty) {
            this.name = name;
            this.a = a;
            this.shadowy = shadowy;
            this.highlighty = highlighty;
        }
    }

    public enum BodyColor implements Custom {
        WHITE(9),
        BLACK(2),
        RED(15),
        GREEN(32),
        BLUE(48),
        PINK(57),
        PEACH(63),
        CYAN(43),
        VIOLET(53),
        ORANGE(23),
        YELLOW(28)
        ;
        public final Color color;
        public final String name;

        BodyColor(int index1) {
            this.color = Constants.RESURRECT_64[index1];
            name = Utils.capitalize(toString());
        }
    }

    public enum FaceType implements Custom {
        DEFAULTM("Basic", 0),
        DEFAULTF("Lashes", 1),
        SLEEP("Sleepy", 2),
        COOL("Cool", 3),
        CRAZY("Crazy", 4),
        DOG("Dog", 5),
        CAT("Cat", 6)
        ;
        public final String name;
        public final int index;

        FaceType(String name, int index) {
            this.name = name;
            this.index = index;
        }
    }

    public enum Accessory implements Custom {
        BUBBLE("Bubble", 0),
        CROWN("Crown", 1),
        ANGEL_WING("Angel", 2)
        ;
        public final String name;
        public final int index;

        Accessory(String name, int index) {
            this.name = name;
            this.index = index;
        }
    }

    public static List<BodyType> unlockedBodyTypes = new ArrayList<>();
    public static List<BodyType> lockedBodyTypes = new ArrayList<>();
    public static List<BodyColor> unlockedBodyColors = new ArrayList<>();
    public static List<BodyColor> lockedBodyColors = new ArrayList<>();
    public static List<FaceType> unlockedFaceTypes = new ArrayList<>();
    public static List<FaceType> lockedFaceTypes = new ArrayList<>();
    public static List<Accessory> unlockedAccessories = new ArrayList<>();
    public static List<Accessory> lockedAccessories = new ArrayList<>();

    static {
        lockedBodyTypes.addAll(Arrays.asList(BodyType.values()));
        lockedBodyColors.addAll(Arrays.asList(BodyColor.values()));
        lockedFaceTypes.addAll(Arrays.asList(FaceType.values()));
        lockedAccessories.addAll(Arrays.asList(Accessory.values()));

        unlock(BodyType.DEFAULT);
        unlock(BodyColor.RED);
        unlock(BodyColor.GREEN);
        unlock(BodyColor.BLUE);
        unlock(FaceType.DEFAULTM);
        unlock(FaceType.DEFAULTF);
    }

    public static void unlock(Custom type) {
        if (type instanceof BodyType) {
            lockedBodyTypes.remove(type);
            unlockedBodyTypes.add((BodyType) type);
        } else if (type instanceof BodyColor) {
            lockedBodyColors.remove(type);
            unlockedBodyColors.add((BodyColor) type);
        } else if (type instanceof FaceType) {
            lockedFaceTypes.remove(type);
            unlockedFaceTypes.add((FaceType) type);
        }
    }

}

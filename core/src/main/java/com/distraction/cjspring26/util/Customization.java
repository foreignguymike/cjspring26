package com.distraction.cjspring26.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Customization {

    public interface Custom {}

    public enum BodyType implements Custom {
        DEFAULT("Slime", "", 0.8f),
        BLOCK("Block", "block", 0.8f),
        MATTE("Matte", "matte", 1),
        FUR("Fur", "fur", 1)
        ;
        public final String name;
        public final String fileName;
        public final float a;

        BodyType(String name, String fileName, float a) {
            this.name = name;
            this.fileName = fileName;
            this.a = a;
        }
    }

    public enum BodyColor implements Custom {
        RED("Red", 15),
        GREEN("Green", 32),
        BLUE("Blue", 48),
        WHITE("White", 9),
        BLACK("Black", 1),
        GRAY("Gray", 7),
        BROWN("Brown", 3),
        GUM("Gum", 57),
        PEACH("Peach", 63),
        CYAN("Cyan", 43),
        PINK("Pink", 53),
        ORANGE("Orange", 23),
        PURPLE("Purple", 50),
        YELLOW("Yellow", 28)
        ;
        public final String name;
        public final Color color;

        BodyColor(String name, int index) {
            this.name = name;
            this.color = Constants.RESURRECT_64[index];
        }
    }

    public enum FaceType implements Custom {
        DEFAULTM("Basic", "basic"),
        DEFAULTF("Lashes", "lashes"),
        SLEEP("Sleepy", "sleepy"),
        COOL("Cool", "cool"),
        CRAZY("Crazy", "crazy"),
        DOG("Dog", "dog"),
        CAT("Cat", "cat"),
        INVERTED("Inverted", "inverted")
        ;
        public final String name;
        public final String fileName;

        FaceType(String name, String fileName) {
            this.name = name;
            this.fileName = fileName;
        }
    }

    // use ordinal for z ordering
    public enum Accessory implements Custom {
        BLUSH("Blush", "blush", 10, -34),
        BUBBLE("Bubble", "bubble", -20, 55),
        CROWN("Crown", "crown", 0, 30),
        HALO("Halo", "halo", 0, 50),
        HORNS("Horns", "horns", -26, 22),
        FLOWER("Flower", "flower", -30, 10),
        RIBBON("Ribbon", "ribbon", -30, 10),
        ANGEL_WINGS("Angel Wings", "angelwings", -80, 0),
        DEMON_WINGS("Demon Wings", "demonwings", -80, 0),
        BEE("Bee", "bee", 0, 0),
        STAR("Star", "star", 0, 0)
        ;
        public final String name;
        public final String fileName;
        public final float x;
        public final float y;

        Accessory(String name, String fileName, float x, float y) {
            this.name = name;
            this.fileName = fileName;
            this.x = x;
            this.y = y;
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

        if (Context.DEBUG) {
            for (BodyType e : BodyType.values()) unlock(e);
            for (BodyColor e : BodyColor.values()) unlock(e);
            for (FaceType e : FaceType.values()) unlock(e);
            for (Accessory e : Accessory.values()) unlock(e);
        }
    }

    public static int totalSize() {
        return lockedBodyTypes.size() + lockedBodyColors.size() + lockedFaceTypes.size() + lockedAccessories.size();
    }

    public static Custom random() {
        int totalSize = totalSize();
        if (totalSize == 0) return null;
        int r = MathUtils.random(totalSize - 1);
        if (r < lockedBodyTypes.size()) return lockedBodyTypes.get(r);
        r -= lockedBodyTypes.size();
        if (r < lockedBodyColors.size()) return lockedBodyColors.get(r);
        r -= lockedBodyColors.size();
        if (r < lockedFaceTypes.size()) return lockedFaceTypes.get(r);
        r -= lockedFaceTypes.size();
        return lockedAccessories.get(r);
    }

    public static void unlock(Custom type) {
        if (type instanceof BodyType) {
            if (lockedBodyTypes.remove(type)) {
                unlockedBodyTypes.add((BodyType) type);
                unlockedBodyTypes.sort(Comparator.comparingInt(Enum::ordinal));
            }
        } else if (type instanceof BodyColor) {
            if (lockedBodyColors.remove(type)) {
                unlockedBodyColors.add((BodyColor) type);
                unlockedBodyColors.sort(Comparator.comparingInt(Enum::ordinal));
            }
        } else if (type instanceof FaceType) {
            if (lockedFaceTypes.remove(type)) {
                unlockedFaceTypes.add((FaceType) type);
                unlockedFaceTypes.sort(Comparator.comparingInt(Enum::ordinal));
            }
        } else if (type instanceof Accessory) {
            if (lockedAccessories.remove(type)) {
                unlockedAccessories.add((Accessory) type);
                unlockedAccessories.sort(Comparator.comparingInt(Enum::ordinal));
            }
        }
    }

}

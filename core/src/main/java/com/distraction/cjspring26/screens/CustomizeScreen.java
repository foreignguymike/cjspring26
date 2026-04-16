package com.distraction.cjspring26.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.entity.NinePatch;
import com.distraction.cjspring26.entity.player.Player;
import com.distraction.cjspring26.entity.player.PlayerRenderer;
import com.distraction.cjspring26.util.Customization;
import com.distraction.cjspring26.util.Utils;

import java.util.List;

public class CustomizeScreen extends Screen {

    private static final int BODY_TYPES_SIZE = Customization.BodyType.values().length;
    private static final int BODY_COLORS_SIZE = Customization.BodyColor.values().length;
    private static final int FACE_TYPES_SIZE = Customization.FaceType.values().length;
    private static final int ACCESSORIES_SIZE = Customization.Accessory.values().length;

    private static final float POP_TIME = 0.3f;
    private static final Interpolation SWING_OUT = new Interpolation.SwingOut(2f);
    private static final Interpolation CLOSE = Interpolation.fastSlow;

    private final Player player;
    private final PlayerRenderer renderer;

    private boolean close = false;
    private float popTime;

    private final NinePatch ninePatch;

    private int index;
    private int bodyTypeIndex;
    private int bodyColorIndex;
    private int faceTypeIndex;
    private int acc1Index;
    private int acc2Index;
    private int acc3Index;

    private final List<Customization.BodyType> bodyTypes = Customization.unlockedBodyTypes;
    private final List<Customization.BodyColor> bodyColors = Customization.unlockedBodyColors;
    private final List<Customization.FaceType> faceTypes = Customization.unlockedFaceTypes;
    private final List<Customization.Accessory> accessories = Customization.unlockedAccessories;

    private final BitmapFont font;
    private final BitmapFont smallFont;

    private float time;

    private final float[] posy = new float[] {
        Constants.HEIGHT2 + 200,
        Constants.HEIGHT2 + 120,
        Constants.HEIGHT2 + 40,
        Constants.HEIGHT2 - 40,
        Constants.HEIGHT2 - 100,
        Constants.HEIGHT2 - 160
    };

    private final TextureRegion arrow;
    private final TextureRegion caret;

    public CustomizeScreen(Context context, Player player) {
        super(context);
        this.player = player;
        renderer = new PlayerRenderer(context);
        renderer.copy(player.playerRenderer);
        renderer.x = Constants.WIDTH2 - 300;
        renderer.y = Constants.HEIGHT2 + 20;

        ninePatch = new NinePatch(
            context,
            context.getImage("dialogcorner"),
            context.getImage("dialogside"),
            context.getPixel(),
            Constants.WIDTH2,
            Constants.HEIGHT2
        );
        ninePatch.x = Constants.WIDTH2;
        ninePatch.y = Constants.HEIGHT2;
        ninePatch.fillColor = Constants.TITLE_BG;

        font = context.getUiFont();
        font.setColor(Constants.BLACK);
        smallFont = context.getDescriptionFont();
        smallFont.setColor(Constants.BLACK);

        bodyTypeIndex = bodyTypes.indexOf(renderer.bodyType);
        bodyColorIndex = bodyColors.indexOf(renderer.bodyColor);
        faceTypeIndex = faceTypes.indexOf(renderer.faceType);
        acc1Index = accessories.indexOf(renderer.acc1);
        acc2Index = accessories.indexOf(renderer.acc2);
        acc3Index = accessories.indexOf(renderer.acc3);

        arrow = context.getImage("customarrow");
        caret = context.getImage("caret");

        transparent = true;

        context.audio.playSound("pop", 0.5f);
    }

    private void pop() {
        context.audio.playSound("pop", 0.5f, MathUtils.random(0.7f, 1.3f));
    }

    private void increment(int increment) {
        int newIndex = index + increment;
        if (newIndex >= 0 && newIndex < posy.length) {
            index = newIndex;
            pop();
        }
    }

    private void incrementBodyTypes(int increment) {
        int newIndex = bodyTypeIndex + increment;
        if (newIndex >= 0 && newIndex < bodyTypes.size()) {
            bodyTypeIndex = newIndex;
            renderer.setBody(bodyTypes.get(bodyTypeIndex));
            pop();
        }
    }

    private void incrementBodyColors(int increment) {
        int newIndex = bodyColorIndex + increment;
        if (newIndex >= 0 && newIndex < bodyColors.size()) {
            bodyColorIndex = newIndex;
            renderer.setBodyColor(bodyColors.get(bodyColorIndex));
            pop();
        }
    }

    private void incrementFaceTypes(int increment) {
        int newIndex = faceTypeIndex + increment;
        if (newIndex >= 0 && newIndex < faceTypes.size()) {
            faceTypeIndex = newIndex;
            renderer.setFaceType(faceTypes.get(faceTypeIndex));
            pop();
        }
    }

    private void incrementAcc1(int increment) {
        int newIndex = acc1Index + increment;
        while (newIndex != -1 && (newIndex == acc2Index || newIndex == acc3Index)) newIndex += increment;
        if (newIndex >= -1 && newIndex < accessories.size()) {
            acc1Index = newIndex;
            if (acc1Index == -1) renderer.setAcc1(null);
            else renderer.setAcc1(accessories.get(acc1Index));
            pop();
        }
    }

    private void incrementAcc2(int increment) {
        int newIndex = acc2Index + increment;
        while (newIndex != -1 && (newIndex == acc1Index || newIndex == acc3Index)) newIndex += increment;
        if (newIndex >= -1 && newIndex < accessories.size()) {
            acc2Index = newIndex;
            if (acc2Index == -1) renderer.setAcc2(null);
            else renderer.setAcc2(accessories.get(acc2Index));
            pop();
        }
    }

    private void incrementAcc3(int increment) {
        int newIndex = acc3Index + increment;
        while (newIndex != -1 && (newIndex == acc1Index || newIndex == acc2Index)) newIndex += increment;
        if (newIndex >= -1 && newIndex < accessories.size()) {
            acc3Index = newIndex;
            if (acc3Index == -1) renderer.setAcc3(null);
            else renderer.setAcc3(accessories.get(acc3Index));
            pop();
        }
    }

    private String getName(Customization.Accessory acc) {
        if (acc == null) return "-";
        else return acc.name;
    }

    @Override
    public void input() {
        if (popTime < POP_TIME) return;
        if (Utils.anyKeysJustPressed(Input.Keys.ENTER, Input.Keys.TAB)) {
            close = true;
            player.playerRenderer.copy(renderer);
            pop();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            increment(-1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            increment(1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            if (index == 0) incrementBodyTypes(-1);
            else if (index == 1) incrementBodyColors(-1);
            else if (index == 2) incrementFaceTypes(-1);
            else if (index == 3) incrementAcc1(-1);
            else if (index == 4) incrementAcc2(-1);
            else if (index == 5) incrementAcc3(-1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            if (index == 0) incrementBodyTypes(1);
            else if (index == 1) incrementBodyColors(1);
            else if (index == 2) incrementFaceTypes(1);
            else if (index == 3) incrementAcc1(1);
            else if (index == 4) incrementAcc2(1);
            else if (index == 5) incrementAcc3(1);
        }
    }

    @Override
    public void update(float dt) {
        if (close) {
            popTime -= dt;
            if (popTime < 0) context.sm.pop();
            ninePatch.scale = CLOSE.apply(popTime / POP_TIME);
        } else {
            if (popTime < POP_TIME) {
                popTime += dt;
                if (popTime > POP_TIME) popTime = POP_TIME;
                ninePatch.scale = SWING_OUT.apply(popTime / POP_TIME);
            }
        }
        ninePatch.update(dt);
        renderer.update(dt);
        time += dt;
    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(0, 0, 0, popTime * 2.5f);
        sb.draw(context.getPixel(), 0, 0, Constants.WIDTH, Constants.HEIGHT);
        sb.setColor(Color.WHITE);
        ninePatch.render(sb);

        if (popTime < POP_TIME) {
            sb.end();
            return;
        }

        renderer.render(sb);

        // labels
        font.draw(sb, "Body Type", Constants.WIDTH2 - 50, posy[0]);
        font.draw(sb, "Body Color", Constants.WIDTH2 - 50, posy[1]);
        font.draw(sb, "Face Type", Constants.WIDTH2 - 50, posy[2]);
        smallFont.draw(sb, "Unlocked " + bodyTypes.size() + "/" + BODY_TYPES_SIZE, Constants.WIDTH2 - 50, posy[0] - 35);
        smallFont.draw(sb, "Unlocked " + bodyColors.size() + "/" + BODY_COLORS_SIZE, Constants.WIDTH2 - 50, posy[1] - 35);
        smallFont.draw(sb, "Unlocked " + faceTypes.size() + "/" + FACE_TYPES_SIZE, Constants.WIDTH2 - 50, posy[2] - 35);
        font.draw(sb, "Item 1", Constants.WIDTH2 - 50, posy[3]);
        font.draw(sb, "Item 2", Constants.WIDTH2 - 50, posy[4]);
        font.draw(sb, "Item 3", Constants.WIDTH2 - 50, posy[5]);
        smallFont.draw(sb, "Unlocked " + accessories.size() + "/" + ACCESSORIES_SIZE, Constants.WIDTH2 - 50, posy[5] - 35);

        // options
        font.draw(sb, renderer.bodyType.name, Constants.WIDTH2 + 200, posy[0]);
        font.draw(sb, renderer.bodyColor.name, Constants.WIDTH2 + 200, posy[1]);
        font.draw(sb, renderer.faceType.name, Constants.WIDTH2 + 200, posy[2]);
        font.draw(sb, getName(renderer.acc1), Constants.WIDTH2 + 200, posy[3]);
        font.draw(sb, getName(renderer.acc2), Constants.WIDTH2 + 200, posy[4]);
        font.draw(sb, getName(renderer.acc3), Constants.WIDTH2 + 200, posy[5]);

        // arrows
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, arrow, Constants.WIDTH2 - 110 + 10 * MathUtils.sin(time * 5), posy[index] - 10);
        Utils.drawCenteredRotated(sb, caret, Constants.WIDTH2 + 160 + 10, posy[index] - 12, 180);
        Utils.drawCentered(sb, caret, Constants.WIDTH2 + 350 + (index > 2 ? 80 : 0), posy[index] - 12);
        sb.end();
    }
}

package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.util.Pulse;
import com.distraction.cjspring26.util.Utils;
import com.distraction.cjspring26.tile.MapData;

public class Inventory extends Entity {

    private static final Interpolation pop = new Interpolation.SwingOut(5f);
    private static final Interpolation pulse = new Pulse(2f);
    private static final float SPEED = 2000f;
    private static final float INC_INTERVAL = 0.2f;
    private static final int MAX = MapData.collectibles.size();

    public boolean start;

    private final TextureRegion offImage;
    private final boolean[] inv;
    private final float[] pos;
    private float time;

    private float incTimer;
    private int collectingIndex = -1;

    public Inventory(Context context) {
        super(context);
        setImage(context.getImage("strawberry"));
        offImage = context.getImage("strawberryoff");
        inv = new boolean[MAX];
        pos = new float[MAX];
        for (int i = 0; i < MAX; i++) pos[i] = 70 + 100 * i;
    }

    public boolean isFinished() {
        for (int i = 0; i < inv.length; i++) {
            if (!inv[i]) return false;
        }
        return true;
    }

    public void collect(int index, float x, float y) {
        collectingIndex = index;
        xdest = pos[index];
        ydest = 70;
        this.x = x;
        this.y = y;
        float dist = getRemainingDistanceE();
        dx = SPEED * (xdest - x) / dist;
        dy = SPEED * (ydest - y) / dist;

        context.audio.playSound("collect", 0.2f);
    }

    @Override
    public void update(float dt) {
        if (start) time += dt;
        if (collectingIndex != -1) {
            boolean reachedDestination = atDestination();
            move(dt);
            if (!reachedDestination && atDestination()) {
                context.audio.playSound("swallow", 0.2f);
                incTimer = INC_INTERVAL;
                inv[collectingIndex] = true;
            }
            if (incTimer > 0) {
                incTimer -= dt;
                if (incTimer < 0) {
                    incTimer = 0;
                    inv[collectingIndex] = true;
                    collectingIndex = -1;
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        for (int i = 0; i < MAX; i++) {
            if (inv[i]) {
                Utils.drawCentered(sb, image, pos[i], 70);
            } else {
                Utils.drawCenteredScaled(
                    sb,
                    offImage,
                    pos[i],
                    70,
                    pop.apply(MathUtils.clamp((time - i * 0.4f) / 0.4f, 0, 1))
                );
            }
        }
        if (collectingIndex != -1) {
            if (atDestination()) {
                Utils.drawCenteredScaled(sb, image, pos[collectingIndex], 70, 1 + pulse.apply(incTimer / INC_INTERVAL));
            } else {
                Utils.drawCentered(sb, image, x, y);
            }
        }
    }
}

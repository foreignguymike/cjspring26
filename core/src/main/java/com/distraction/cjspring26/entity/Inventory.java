package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Utils;
import com.distraction.cjspring26.tile.MapData;

public class Inventory extends Entity {

    private static final float SPEED = 2000f;
    private static final float INC_INTERVAL = 0.2f;
    private static final int MAX = MapData.collectibles.size();

    private final TextureRegion offImage;
    private final boolean[] inv;
    private final float[] pos;

    private float incTimer;
    private int collectingIndex;

    public Inventory(Context context) {
        super(context);
        setImage(context.getImage("strawberry"));
        offImage = context.getImage("strawberryoff");
        inv = new boolean[MAX];
        pos = new float[MAX];
        for (int i = 0; i < MAX; i++) pos[i] = 70 + 100 * i;
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
                float scale = collectingIndex == i ? 1 + incTimer * 3f : 1f;
                Utils.drawCentered(sb, image, pos[i], 70, scale);
            } else {
                Utils.drawCentered(sb, offImage, pos[i], 70);
            }
        }
        if (collectingIndex != -1 && !atDestination()) {
            Utils.drawCentered(sb, image, x, y);
        }
    }
}

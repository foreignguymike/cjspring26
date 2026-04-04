package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Utils;

public class Inventory extends Entity {

    private final TextureRegion slot;
    private final TextureRegion slotSelected;
    private final TextureRegion ladderInv;

    private final boolean[] inv;

    private int selectedIndex = -1;

    public Inventory(Context context) {
        slot = context.getImage("slot");
        slotSelected = context.getImage("slotselected");
        ladderInv = context.getImage("ladderinv");
        w = slot.getRegionWidth();
        h = slot.getRegionHeight();

        inv = new boolean[5];
        inv[0] = true;
        inv[1] = true;
    }

    public boolean isLadderSelected() {
        if (selectedIndex < 0) return false;
        return inv[selectedIndex];
    }

    public boolean canAddLadder() {
        for (boolean b : inv) {
            if (!b) return true;
        }
        return false;
    }

    public void removeLadder() {
        if (selectedIndex < 0) return;
        if (inv[selectedIndex]) {
            inv[selectedIndex] = false;
            selectedIndex = -1;
            return;
        }
    }

    public void addLadder() {
        for (int i = 0; i < inv.length; i++) {
            if (!inv[i]) {
                inv[i] = true;
                return;
            }
        }
    }

    public void setSelectedIndex(int index) {
        if (selectedIndex == index - 1) selectedIndex = -1;
        else selectedIndex = index - 1;
    }

    @Override
    public void render(SpriteBatch sb) {
        for (int i = 0; i < inv.length; i++) {
            float x = Constants.WIDTH / 2f - ((int) (inv.length / 2) - i) * w * 1.2f;
            Utils.drawCentered(sb, selectedIndex == i ? slotSelected : slot, x, h);
            if (inv[i]) Utils.drawCentered(sb, ladderInv, x, h);
        }
    }
}

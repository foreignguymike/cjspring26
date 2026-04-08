package com.distraction.cjspring26.util;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;

public class Pulse extends Interpolation {

    private final float amp;

    public Pulse(float amp) {
        this.amp = amp;
    }

    @Override
    public float apply(float a) {
        return amp * MathUtils.sin(MathUtils.PI * a);
    }
}

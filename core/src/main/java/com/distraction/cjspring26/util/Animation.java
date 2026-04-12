package com.distraction.cjspring26.util;

public class Animation<T> {

    private T[] frames;
    private int frameIndex;

    private final float duration;
    private float time;

    public Animation(T[] frames, float duration) {
        set(frames);
        this.duration = duration;
    }

    public void set(T[] frames) {
        if (this.frames == frames) return;
        this.frames = frames;
        frameIndex = 0;
        time = 0;
    }

    public void update(float dt) {
        time += dt;
        if (time > duration) {
            time = 0;
            frameIndex++;
            if (frameIndex == frames.length) frameIndex = 0;
        }
    }

    public T get() {
        return frames[frameIndex];
    }

}

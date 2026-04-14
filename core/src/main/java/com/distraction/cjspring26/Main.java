package com.distraction.cjspring26;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private Context context;

    @Override
    public void create() {
        context = new Context();

        Gdx.input.setCatchKey(Input.Keys.UP, true);
        Gdx.input.setCatchKey(Input.Keys.DOWN, true);
        Gdx.input.setCatchKey(Input.Keys.LEFT, true);
        Gdx.input.setCatchKey(Input.Keys.RIGHT, true);
        Gdx.input.setCatchKey(Input.Keys.SPACE, true);
        Gdx.input.setCatchKey(Input.Keys.NUM_1, true);
        Gdx.input.setCatchKey(Input.Keys.NUM_2, true);
        Gdx.input.setCatchKey(Input.Keys.NUM_3, true);
        Gdx.input.setCatchKey(Input.Keys.NUM_4, true);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();
        Cursor invisibleCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(invisibleCursor);
        pixmap.dispose();
    }

    @Override
    public void resize(int width, int height) {
        context.sm.resize(width, height);
    }

    @Override
    public void render() {
        context.sm.input();
        context.sm.update(Gdx.graphics.getDeltaTime());
        context.sm.render();
    }

    @Override
    public void dispose() {
        context.dispose();
    }
}

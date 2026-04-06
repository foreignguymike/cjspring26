package com.distraction.cjspring26.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.entity.Background;
import com.distraction.cjspring26.entity.Player;
import com.distraction.cjspring26.tile.TileMap;

public class PlayScreen extends Screen {

    private final Background textureBg;

    private final TileMap tileMap;
    private final Player player;

    public PlayScreen(Context context) {
        super(context);

        textureBg = new Background(context, cam, context.getImage("papermache"), Constants.TEXTURE_OPACITY);

        tileMap = new TileMap(context, cam, uiCam);
        player = new Player(context, tileMap);

        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        ignoreInput = true;
        in.start();
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f);

        context.audio.stopMusic();
        context.audio.playMusic("main", 0.3f, true);
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        player.up = Gdx.input.isKeyPressed(Input.Keys.UP);
        player.down = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        player.left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        player.right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);

        player.update(dt);
        cam.position.set(player.x, player.y, 0);
        cam.update();

        tileMap.update(dt);
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Constants.WATER);
        sb.draw(context.getPixel(), 0, 0, Constants.WIDTH, Constants.HEIGHT);

        sb.setProjectionMatrix(cam.combined);
        sb.setColor(Color.WHITE);

        tileMap.render(sb);

        player.render(sb);
        tileMap.renderCollectibles(sb);

        textureBg.render(sb);

        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Color.WHITE);
        player.inventory.render(sb);

        in.render(sb);
        out.render(sb);

        sb.end();
    }
}

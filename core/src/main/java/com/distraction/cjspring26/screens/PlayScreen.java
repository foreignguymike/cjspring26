package com.distraction.cjspring26.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.entity.Background;
import com.distraction.cjspring26.entity.Collectible;
import com.distraction.cjspring26.entity.Player;
import com.distraction.cjspring26.tile.TileMap;

import java.util.List;

public class PlayScreen extends Screen {

    private final Background textureBg;

    private final TileMap tileMap;
    private final Player player;

    public PlayScreen(Context context) {
        super(context);

        textureBg = new Background(cam, context.getImage("papermache"), Constants.TEXTURE_OPACITY);

        tileMap = new TileMap(context);
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) player.action();
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) player.inventory.setSelectedIndex(1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) player.inventory.setSelectedIndex(2);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) player.inventory.setSelectedIndex(3);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) player.inventory.setSelectedIndex(4);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) player.inventory.setSelectedIndex(5);
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);

        player.update(dt);
        cam.position.set(
            MathUtils.clamp(player.x, Constants.WIDTH / 2f, tileMap.getTotalWidth() - Constants.WIDTH / 2f),
            MathUtils.clamp(player.y, Constants.HEIGHT / 2f, tileMap.getTotalHeight() - Constants.HEIGHT / 2f),
            0
        );
        cam.update();

        for (int i = 0; i < tileMap.collectibles.size(); i++) {
            Collectible c = tileMap.collectibles.get(i);
            if (player.collect(c)) {
                tileMap.collectibles.remove(i);
                i--;
            }
        }
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Constants.BLUE);
        sb.draw(context.getPixel(), 0, 0, Constants.WIDTH, Constants.HEIGHT);

        sb.setProjectionMatrix(cam.combined);
        sb.setColor(Color.WHITE);

        tileMap.render(sb);

        player.render(sb);

        textureBg.render(sb);

        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Color.WHITE);
        player.inventory.render(sb);

        in.render(sb);
        out.render(sb);

        sb.end();
    }
}

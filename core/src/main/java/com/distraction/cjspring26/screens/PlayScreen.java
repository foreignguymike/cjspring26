package com.distraction.cjspring26.screens;

import com.badlogic.gdx.graphics.Color;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.entity.Background;
import com.distraction.cjspring26.entity.player.Player;
import com.distraction.cjspring26.screens.scenes.IntroScene;
import com.distraction.cjspring26.screens.scenes.Scene;
import com.distraction.cjspring26.tile.TileMap;

public class PlayScreen extends Screen {

    private Scene scene = null;

    private final Background textureBg;

    public final TileMap tileMap;
    public Player player;
    public Player stuck;

    public PlayScreen(Context context) {
        super(context);

        textureBg = new Background(context, cam, context.getImage("papermache"), Constants.TEXTURE_OPACITY);

        tileMap = new TileMap(context, cam, uiCam);
        player = new Player(context, tileMap);
        player.setTile(9, 0);
//        player.setTile(7, 60); // test
        stuck = new Player(context, tileMap);
        stuck.setTile(9, 68);
        stuck.mirror = true;

        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        ignoreInput = true;
        in.start();
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f);

        setScene(new IntroScene(this));

        context.audio.stopMusic();
        context.audio.playMusic("main", 0.15f, true);
        context.audio.playMusic("beach", 0.15f, true);
    }

    public void playerSwap() {
        stuck = player;
        player = new Player(context, tileMap);
        player.setTile(9, 0);
    }

    public void setScene(Scene scene) {
        if (this.scene != null) this.scene.exit();
        this.scene = scene;
        this.scene.enter();
    }

    @Override
    public void input() {
        if (ignoreInput) return;
        scene.input();
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);

        scene.update(dt);

        player.update(dt);
        stuck.update(dt);
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
        stuck.render(sb);
        tileMap.renderCollectibles(sb);

        scene.render(sb);

//        textureBg.render(sb);

        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Color.WHITE);
        tileMap.inventory.render(sb);

        in.render(sb);
        out.render(sb);

        sb.end();
    }
}

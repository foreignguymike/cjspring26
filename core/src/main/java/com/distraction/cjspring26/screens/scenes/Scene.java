package com.distraction.cjspring26.screens.scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.cjspring26.entity.Player;
import com.distraction.cjspring26.screens.PlayScreen;
import com.distraction.cjspring26.tile.TileMap;

public class Scene {

    protected final PlayScreen screen;
    protected final OrthographicCamera cam;
    protected final Player player;
    protected final Player stuck;
    protected final TileMap tileMap;

    public Scene(PlayScreen screen) {
        this.screen = screen;
        this.cam = screen.cam;
        this.player = screen.player;
        this.stuck = screen.stuck;
        this.tileMap = screen.tileMap;
    }

    public void enter() {}
    public void exit() {}
    public void input() {}
    public void update(float dt) {}
    public void render(SpriteBatch sb) {}

}

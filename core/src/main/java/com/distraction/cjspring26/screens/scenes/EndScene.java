package com.distraction.cjspring26.screens.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.entity.Dialog;
import com.distraction.cjspring26.screens.PlayScreen;import com.distraction.cjspring26.util.Utils;

public class EndScene extends Scene {

    private static final float CAM_LERP = 3f;

    private enum Stage {
        PLAYER_MOVE1,
        STUCK_DIALOG1,
        STUCK_MOVE1,
        STUCK_MOVE2,
        STUCK_DIALOG2,
        PLAYER_DIALOG1,
        PLAYER_MOVE2,
        STUCK_DIALOG3,
        PLAYER_DIALOG2
    }

    private final Vector3 camTarget = new Vector3();

    private final int destRow = 9;
    private final int destCol = 66;
    private final int switchCol = 67;
    private final int finalCol = 73;
    private Stage stage = Stage.PLAYER_MOVE1;

    private float time;

    private Dialog dialog;
    private final Dialog stuckDialog1;
    private final Dialog stuckDialog2;
    private final Dialog playerDialog1;
    private final Dialog stuckDialog3;
    private final Dialog playerDialog2;

    private boolean skipping;
    private float skipTimer;

    public EndScene(PlayScreen screen) {
        super(screen);

        stuckDialog1 = new Dialog(
            screen.context,
            new String[]{
                "You made it!",
                "So...",
                "Someone has\nto stand",
                "on this switch\nto exit."
            },
            stuck
        );
        stuckDialog2 = new Dialog(
            screen.context,
            new String[]{
                "See?",
                "If you step\non it...",
                "I can\nleave...",
                "and come\nback with help!",
            },
            stuck
        );
        playerDialog1 = new Dialog(
            screen.context,
            new String[]{
                "Ok...",
            },
            player
        );
        stuckDialog3 = new Dialog(
            screen.context,
            new String[]{
                "Thanks...",
                "SUCKER!!"
            },
            stuck
        );
        stuckDialog3.lock = true;
        playerDialog2 = new Dialog(
            screen.context,
            new String[]{
                "HEY!!!",
                "..."
            },
            player
        );

        dialog = stuckDialog1;
    }

    @Override
    public void enter() {
        player.reset();
    }

    @Override
    public void input() {
        if (Utils.anyKeysJustPressed(Input.Keys.ENTER, Input.Keys.SPACE)) {
            if (dialog != null) dialog.next();
        }
        skipping = Gdx.input.isKeyPressed(Input.Keys.ENTER);
    }

    @Override
    public void update(float dt) {
        if (skipping) {
            skipTimer += dt;
            if (skipTimer > 1) {
                leave();
            }
        } else {
            skipTimer = 0;
        }
        if (stage == Stage.PLAYER_MOVE1) {
            player.up = player.row < destRow;
            player.down = player.row > destRow;
            player.right = player.col != destCol;
            if (player.row == destRow && player.col == destCol && player.atDestination()) {
                stage = Stage.STUCK_DIALOG1;
                dialog.next();
            }
        } else if (stage == Stage.STUCK_DIALOG1) {
            if (dialog.isDone()) {
                stage = Stage.STUCK_MOVE1;
                time = 1;
            }
        } else if (stage == Stage.STUCK_MOVE1) {
            stuck.left = stuck.col != switchCol;
            if (stuck.col == switchCol && stuck.atDestination()) {
                time -= dt;
                if (time < 0) {
                    stage = Stage.STUCK_MOVE2;
                }
            }
        } else if (stage == Stage.STUCK_MOVE2) {
            stuck.right = stuck.col != switchCol + 1;
            if (stuck.col == switchCol + 1 && stuck.atDestination()) {
                stage = Stage.STUCK_DIALOG2;
                stuck.mirror = true;
                dialog = stuckDialog2;
                dialog.next();
            }
        } else if (stage == Stage.STUCK_DIALOG2) {
            if (dialog.isDone()) {
                stage = Stage.PLAYER_DIALOG1;
                dialog = playerDialog1;
                dialog.next();
            }
        } else if (stage == Stage.PLAYER_DIALOG1) {
            if (dialog.isDone()) {
                stage = Stage.PLAYER_MOVE2;
                time = 0.5f;
            }
        } else if (stage == Stage.PLAYER_MOVE2) {
            player.right = player.col != switchCol;
            if (player.col == switchCol && stuck.atDestination()) {
                time -= dt;
                if (time < 0) {
                    stage = Stage.STUCK_DIALOG3;
                    dialog = stuckDialog3;
                    dialog.next();
                }
            }
        } else if (stage == Stage.STUCK_DIALOG3) {
            if (dialog.isTextDone()) {
                stuck.right = stuck.col != finalCol;
                if (stuck.col == finalCol && stuck.atDestination()) {
                    stage = Stage.PLAYER_DIALOG2;
                    dialog = playerDialog2;
                    dialog.next();
                }
            }
        } else if (stage == Stage.PLAYER_DIALOG2) {
            if (dialog.isTextDone()) {
                player.right = player.col != switchCol + 1;
                time = 1f;
            }
            if (dialog.isDone()) {
                time -= dt;
                if (time < 0) {
                    leave();
                }
            }
        }

        camTarget.set(
            MathUtils.clamp(player.x, Constants.WIDTH2, tileMap.getPlayableWidth() - Constants.WIDTH2),
            player.y,
            0
        );
        cam.position.lerp(camTarget, CAM_LERP * dt);
        cam.update();
        dialog.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!dialog.isDone()) dialog.render(sb);
    }

    private void leave() {
        player.setTile(destRow, switchCol + 1);
        player.mirror = true;
        screen.playerSwap();
        screen.setScene(new HelpScene(screen));
    }
}

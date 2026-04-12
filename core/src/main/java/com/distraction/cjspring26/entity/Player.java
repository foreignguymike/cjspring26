package com.distraction.cjspring26.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.cjspring26.Constants;
import com.distraction.cjspring26.Context;
import com.distraction.cjspring26.Direction;
import com.distraction.cjspring26.util.Animation;
import com.distraction.cjspring26.util.Utils;
import com.distraction.cjspring26.tile.TileMap;

public class Player extends TileEntity {

    private static final float SPEED = 500;
    private static final float OFFSET_Y = 20;

    public boolean debug;

    private final Animation<TextureRegion[]> animation;
    private final TextureRegion[][] idle;
    private final TextureRegion[][] walk;
    private final TextureRegion[][] jump;

    public Color skinColor = Constants.SKIN1;

    private PlayerData.Face face = PlayerData.Face.DEFAULT;
    private TextureRegion[] faceImages;
    private Color eyeColor = Constants.WISTERIC_17[4];

    private PlayerData.Hair hair = PlayerData.Hair.ANIME;
    private TextureRegion[] hairImages;
    private Color hairColor = Constants.WISTERIC_17[7];

    private boolean moving;

    private boolean jumping;
    private float jumpy;
    private float totalDistance;

    public boolean up, down, left, right;
    public boolean mirror;

    public Player(Context context, TileMap tileMap) {
        super(context, tileMap);

        w = 100;
        h = 150;
        TextureRegion[] outlineSprites = context.getImage("playeroutline").split(100, 150)[0];
        TextureRegion[] fillSprites = context.getImage("playerfill").split(100, 150)[0];


        idle = new TextureRegion[][] {
            { outlineSprites[0], fillSprites[0] }
        };
        walk = new TextureRegion[][] {
            { outlineSprites[1], fillSprites[1] },
            { outlineSprites[2], fillSprites[2] },
            { outlineSprites[3], fillSprites[3] },
            { outlineSprites[4], fillSprites[4] }
        };
        jump = new TextureRegion[][] {
            { outlineSprites[5], fillSprites[5] }
        };
        animation = new Animation<>(idle, 1 / 8f);

        setFace(PlayerData.Face.DEFAULT, Constants.WISTERIC_17[16]);
        setHair(PlayerData.Hair.ANIME, Constants.WISTERIC_17[0]);
    }

    public void setFace(PlayerData.Face face, Color color) {
        this.face = face;
        faceImages = new TextureRegion[] {
            context.getImage(face.nameOutline),
            context.getImage(face.nameFill)
        };
        this.eyeColor = color;
    }

    public void setHair(PlayerData.Hair hair, Color color) {
        this.hair = hair;
        hairImages = new TextureRegion[] {
            context.getImage(hair.nameOutline),
            context.getImage(hair.nameFill)
        };
        this.hairColor = color;
    }

    public void reset() {
        up = down = left = right = false;
    }

    @Override
    public void update(float dt) {
        // can only move if not yet moving
        if (!moving) {
            int dist = 0;
            if (up) {
                dist = tileMap.getTravelDistance(row, col, Direction.UP);
                if (dist > 0) {
                    tileMap.playerLeft(row, col);
                    row += dist;
                    ydest = tileMap.coord(row);
                    totalDistance = getRemainingDistanceM();
                    dx = 0;
                    dy = SPEED * (dist > 1 ? 1.2f : 1);
                }
            } else if (down) {
                dist = tileMap.getTravelDistance(row, col, Direction.DOWN);
                if (dist > 0) {
                    tileMap.playerLeft(row, col);
                    row -= dist;
                    ydest = tileMap.coord(row);
                    totalDistance = getRemainingDistanceM();
                    dx = 0;
                    dy = -SPEED * (dist > 1 ? 1.2f : 1);
                }
            } else if (left) {
                mirror = true;
                dist = tileMap.getTravelDistance(row, col, Direction.LEFT);
                if (dist > 0) {
                    tileMap.playerLeft(row, col);
                    col -= dist;
                    xdest = tileMap.coord(col);
                    totalDistance = getRemainingDistanceM();
                    dx = -SPEED * (dist > 1 ? 1.2f : 1);
                    dy = 0;
                }
            } else if (right) {
                mirror = false;
                dist = tileMap.getTravelDistance(row, col, Direction.RIGHT);
                if (dist > 0) {
                    tileMap.playerLeft(row, col);
                    col += dist;
                    xdest = tileMap.coord(col);
                    totalDistance = getRemainingDistanceM();
                    dx = SPEED * (dist > 1 ? 1.2f : 1);
                    dy = 0;
                }
            } else {
                dx = dy = 0;
            }
            jumping = dist > 1;
        }

        boolean reachedDestination = atDestination();

        // move
        move(dt);

        // just reached destination
        if (!reachedDestination && atDestination()) {
            tileMap.playerLanded(row, col);
        }

        // prevent moving while already moving or changing direction
        moving = x != xdest || y != ydest;

        // calculate jump
        if (!moving) jumping = false;
        jumpy = (jumping ? 100 : 5) * MathUtils.sin(3.14f * getRemainingDistanceM() / totalDistance);

        if (jumping) {
            animation.set(jump);
        } else if (!reachedDestination) {
            animation.set(walk);
        } else {
            animation.set(idle);
        }
        animation.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        float y = this.y + jumpy + OFFSET_Y;
        // body
        sb.setColor(skinColor);
        Utils.drawCentered(sb, animation.get()[1], x, y, mirror);
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, animation.get()[0], x, y, mirror);

        // face
        sb.setColor(eyeColor);
        Utils.drawCentered(sb, faceImages[1], x + face.x * (mirror ? -1 : 1), y + face.y, mirror);
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, faceImages[0], x + face.x * (mirror ? -1 : 1), y + face.y, mirror);

        // hair
        sb.setColor(hairColor);
        Utils.drawCentered(sb, hairImages[1], x + hair.x * (mirror ? -1 : 1), y + hair.y, mirror);
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, hairImages[0], x + hair.x * (mirror ? -1 : 1), y + hair.y, mirror);
    }
}

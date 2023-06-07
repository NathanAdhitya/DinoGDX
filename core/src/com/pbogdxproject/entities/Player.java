package com.pbogdxproject.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.pbogdxproject.interfaces.EntityLifecycle;

public class Player extends Rectangle implements EntityLifecycle {
    final static float gravity = 980f;
    private static final int FRAME_COLS = 3;
    Texture texture = new Texture(Gdx.files.internal("t-rex.png"));
    Sprite sprite = new Sprite(texture);
    Animation<TextureRegion> runningAnimation;
    Texture runningAnimationSheet = new Texture(Gdx.files.internal("t-rex-anim.png"));
    float stateTime = 0f;
    float yVelocity = 0;
    boolean isOnGround = true;

    float yLowerBound = 100;

    public Player() {
        width = 80;
        height = 86;
        x = 100;

        TextureRegion[][] tmp = TextureRegion.split(runningAnimationSheet,
            runningAnimationSheet.getWidth() / FRAME_COLS
            , runningAnimationSheet.getHeight());

        TextureRegion[] runningFrames = new TextureRegion[2];
        for (int i = 1, n = 0; i < tmp[0].length; i++) {
            runningFrames[n++] = tmp[0][i];
        }
        runningAnimation = new Animation<>(0.1f, runningFrames);
    }

    public void tick(float delta) {
        physicsTick(delta);
    }

    private void physicsTick(float dt) {
        yVelocity += -gravity * dt;
        y += yVelocity * dt;

        if (y <= yLowerBound) {
            yVelocity = 0;
            y = yLowerBound;
            isOnGround = true;

            // Eligible for jumping
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                yVelocity = 300;
            }
        } else {
            isOnGround = false;
        }
    }

    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        if(!isOnGround){
            batch.draw(sprite, x, y);
        } else {
            TextureRegion currentFrame = runningAnimation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, x, y);
        }

    }

    public void dispose() {
        texture.dispose();
    }
}

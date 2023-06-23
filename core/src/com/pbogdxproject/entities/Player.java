package com.pbogdxproject.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.pbogdxproject.GameState;
import com.pbogdxproject.interfaces.Lifecycle;

public class Player extends Rectangle implements Lifecycle {
    final static float gravity = 12f;
    private static final int FRAME_COLS = 3;
    Texture texture = new Texture(Gdx.files.internal("textures/t-rex.png"));
    Sprite sprite = new Sprite(texture);
    Animation<TextureRegion> runningAnimation;
    Texture runningAnimationSheet = new Texture(Gdx.files.internal("textures/t-rex-anim.png"));
    float stateTime = 0f;
    float yVelocity = 0;
    boolean isOnGround = true;
    float yLowerBound = 100;

    RectangleCollider[][] colliders = {
            {
                    // Running state
                    new RectangleCollider(0f, 0f, 80f, 86f, new Offset2D(0, 48, 0, 30)),
                    new RectangleCollider(0f, 0f, 80f, 86f, new Offset2D(0, 0, 32, 20)),
                    new RectangleCollider(0f, 0f, 80f, 86f, new Offset2D(30, 35, 20, 0))
            },
            {
                    // TODO: Ducking stage

            }
    };

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

    @Override
    public void init() {

    }

    public RectangleCollider[] getActiveColliders() {
        return colliders[0];
    }

    public void updateColliders() {
        for (RectangleCollider activeCollider : getActiveColliders()) {
            activeCollider.calculateBounds(x, y, width, height, 1);
        }
    }

    public void tick(float delta) {
        physicsTick(delta);
        updateColliders();
    }

    private void physicsTick(float dt) {
        yVelocity += -gravity * dt * GameState.physicsScale;
        y += yVelocity * dt;

        if (y <= yLowerBound) {
            yVelocity = 0;
            y = yLowerBound;
            isOnGround = true;

            // Eligible for jumping
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                yVelocity = 4f * GameState.physicsScale;
            }
        } else {
            isOnGround = false;
        }
    }

    public void render(SpriteBatch batch) {
        if(GameState.isAlive)
            stateTime += Gdx.graphics.getDeltaTime() * GameState.scrollSpeed;

        if (!isOnGround) {
            batch.draw(sprite, x, y);
        } else {
            TextureRegion currentFrame = runningAnimation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, x, y);
        }

        for (RectangleCollider activeCollider : getActiveColliders()) {
            activeCollider.render(batch);
        }
    }

    public void dispose() {
        texture.dispose();
    }
}

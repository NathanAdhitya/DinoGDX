package com.pbogdxproject.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.pbogdxproject.GameConstants;
import com.pbogdxproject.GameState;
import com.pbogdxproject.GameStatus;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.interfaces.Lifecycle;

public class Player extends Rectangle implements Lifecycle {
    final static float gravity = 12f;
    private static final int FRAME_COLS = 6;
    Texture texture = new Texture(Gdx.files.internal("textures/dino.png"));
    Sprite sprite;
    Animation<TextureRegion> runningAnimation;
    Texture runningAnimationSheet = new Texture(Gdx.files.internal("textures/dino.png"));
    float stateTime = 0f;
    float yVelocity = 0;
    boolean isOnGround = true;
    float yLowerBound = 100;
    Music jumpSound;

    TextureRegion[] textures;

    RectangleCollider[][] colliders = {
        {
            // Running state
            new RectangleCollider(0f, 0f, 0f, 0f, new Offset2D(4, 60, 4, 34)),
            new RectangleCollider(0f, 0f, 0f, 0f, new Offset2D(0, 4, 36, 24)),
            new RectangleCollider(0f, 0f, 0f, 0f, new Offset2D(32, 36, 24, 4))
        },
        {
            // TODO: Ducking stage

        }
    };

    public Player() {
        width = runningAnimationSheet.getWidth() / FRAME_COLS;
        height = runningAnimationSheet.getHeight();

        // Set each collider width and height to the width and height of the sprite
        for (RectangleCollider[] collider : colliders) {
            for (RectangleCollider activeCollider : collider) {
                activeCollider.width = width;
                activeCollider.height = height;
            }
        }

        x = 100;

        TextureRegion[][] tmp = TextureRegion.split(runningAnimationSheet,
            runningAnimationSheet.getWidth() / FRAME_COLS
            , runningAnimationSheet.getHeight()
        );

        TextureRegion[] runningFrames = new TextureRegion[2];
        textures = tmp[0];

        for (int i = 2, n = 0; i <= 3; i++) {
            runningFrames[n++] = tmp[0][i];
        }
        runningAnimation = new Animation<>(0.075f, runningFrames);

        sprite = new Sprite(tmp[0][0]);
    }

    @Override
    public void init() {
        jumpSound = MyGdxGame.assets.get("sounds/jump.wav", Music.class);
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
        yVelocity += -gravity * dt * GameConstants.PHYSICS_MULTIPLIER;
        y += yVelocity * dt;

        if (y <= yLowerBound) {
            yVelocity = 0;
            y = yLowerBound;
            isOnGround = true;

            // Eligible for jumping
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                yVelocity = 4f * GameConstants.PHYSICS_MULTIPLIER;
                jumpSound.play();
            }
        } else {
            isOnGround = false;
        }
    }

    public void render(SpriteBatch batch) {
        if (GameState.status == GameStatus.PLAYING)
            stateTime += Gdx.graphics.getDeltaTime();

        if (GameState.status == GameStatus.DEAD){
            batch.draw(textures[4], x, y);
            return;
        }

        if (!isOnGround || GameState.scrollSpeed == 0) {
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

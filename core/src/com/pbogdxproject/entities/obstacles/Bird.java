package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pbogdxproject.GameConstants;
import com.pbogdxproject.GameState;
import com.pbogdxproject.GameStatus;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.entities.utils.Obstacle;
import com.pbogdxproject.entities.utils.Offset2D;
import com.pbogdxproject.entities.utils.RectangleCollider;

public class Bird extends Obstacle {
    final private static RectangleCollider[] COLLIDERS = {
        new RectangleCollider(new Offset2D(10, 12, 10, 12))
    };
    final float[] BIRD_HEIGHTS = {1.3f, 2f, 2.5f, 2.5f, 2f};
    private TextureRegion[] animationFrames;
    private Animation<TextureRegion> animation;
    private float stateTime = 0;

    @Override
    public void init() {
        y = this.BIRD_HEIGHTS[GameState.RANDOM.nextInt(this.BIRD_HEIGHTS.length)] * GameConstants.METERS_TO_PIXELS_MULTIPLIER;
        texture = MyGdxGame.assets.get("textures/bird.png", Texture.class);
        speed = 210;

        Texture runningAnimationSheet = MyGdxGame.assets.get("textures/bird-anim.png", Texture.class);

        TextureRegion[][] tmp = TextureRegion.split(
            runningAnimationSheet,
            runningAnimationSheet.getWidth() / 2
            , runningAnimationSheet.getHeight()
        );


        animationFrames = new TextureRegion[2];
        for (int i = 0, n = 0; i < tmp[0].length; i++) {
            animationFrames[n++] = tmp[0][i];
        }

        animation = new Animation<TextureRegion>(0.15f, animationFrames);

        colliders = COLLIDERS;
        super.init();
    }

    @Override
    public void tick(float delta) {
        stateTime += delta;
        super.tick(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        // Render
        if (GameState.status == GameStatus.PLAYING) {
            batch.draw(animation.getKeyFrame(stateTime, true), x, y);
        } else {
            batch.draw(animationFrames[0], x, y);
        }

        renderColliders(batch);
    }

}

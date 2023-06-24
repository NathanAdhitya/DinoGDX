package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.entities.utils.Obstacle;
import com.pbogdxproject.entities.utils.Offset2D;
import com.pbogdxproject.entities.utils.RectangleCollider;

import java.util.Random;

public class Bird extends Obstacle {
    final private static RectangleCollider[] COLLIDERS = {
        new RectangleCollider(new Offset2D(0, 0, 15, 15)),
        new RectangleCollider(new Offset2D(20, 30, 0, 0))
    };
    int width;
    int height;
    int[] yValues = {1, 10}; // diisi sama tinggi e burung e mau berapa
    int yValue;

    public Bird() {
        width = 60;
        height = 54;

        Random rndm = new Random();
        this.yValue = this.yValues[rndm.nextInt(this.yValues.length)];

        Texture runningAnimationSheet = new Texture(Gdx.files.internal("textures/bird-anim.png"));

        TextureRegion[][] tmp = TextureRegion.split(runningAnimationSheet,
            runningAnimationSheet.getWidth() / 2
            , runningAnimationSheet.getHeight()
        );

        TextureRegion[] runningFrames = new TextureRegion[2];
        for (int i = 1, n = 0; i < tmp[0].length; i++) {
            runningFrames[n++] = tmp[0][i];
        }
    }

    @Override
    public void init() {
        texture = MyGdxGame.assets.get("textures/bird.png", Texture.class);
        y = 100;

        colliders = COLLIDERS;
        super.init();
    }

    @Override
    public void tick(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void onPlayerCollision() {

    }
}

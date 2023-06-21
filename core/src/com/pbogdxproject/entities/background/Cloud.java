package com.pbogdxproject.entities.background;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.pbogdxproject.entities.Entity;

import java.util.Random;

public class Cloud extends Entity implements Pool.Poolable {

    public Vector2 position;
    public boolean alive;
    public float scale;
    public float speed;
    static Random rand = new Random();
    private final Texture texture = new Texture("textures/cloud.png");

    @Override
    public void init() {
        position = new Vector2();
        alive = true;

        // Random scale from 0.25 to 1
        scale = rand.nextFloat() * 0.75f + 0.25f;
    }

    @Override
    public void tick(float delta) {
        // Move cloud
        if (alive) {
            position.x -= speed * delta;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // Only render if alive
        if (alive) {
            batch.draw(texture, position.x, position.y);
        }
    }

    @Override
    public void reset() {
        alive = false;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}

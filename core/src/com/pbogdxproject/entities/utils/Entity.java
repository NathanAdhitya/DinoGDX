package com.pbogdxproject.entities.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import com.pbogdxproject.GameState;
import com.pbogdxproject.interfaces.Lifecycle;

abstract public class Entity implements Lifecycle, Pool.Poolable {
    /**
     * Absolute position of entity.
     */
    public float x = 0, y = 0;
    protected int width, height;
    protected Texture texture = null;
    protected float speed = 200;
    boolean isAlive = false;
    private float scale = 1;

    public void init() {
        isAlive = true;

        recalculateSize();
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;

        // Recalculate width and height.
        recalculateSize();
    }

    protected void recalculateSize() {
        width = (int) (texture.getWidth() * scale);
        height = (int) (texture.getHeight() * scale);
    }

    protected void setTexture(String filename) {
        this.texture = new Texture(Gdx.files.internal(filename));
    }

    @Override
    public void reset() {
        isAlive = false;
    }

    @Override
    public void tick(float delta) {
        if (!isAlive) return;
        x -= speed * delta * GameState.scrollSpeed;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!isAlive) return;
        batch.draw(texture, x, y, width, height);
    }
}

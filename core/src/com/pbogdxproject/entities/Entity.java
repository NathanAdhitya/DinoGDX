package com.pbogdxproject.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.pbogdxproject.GameState;
import com.pbogdxproject.interfaces.Lifecycle;

abstract public class Entity implements Lifecycle, Pool.Poolable {
    /**
     * Absolute position of entity.
     */
    public float x = 0, y = 0;
    private float scale = 1;
    protected int width, height;
    float speed = 200;
    protected Texture texture = null;
    boolean isAlive = false;

    public void init() {
        isAlive = true;

        recalculateSize();
    }

    public float getScale(){
        return scale;
    }

    public void setScale(float scale){
        this.scale = scale;

        // Recalculate width and height.
        recalculateSize();
    }

    protected void recalculateSize(){
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

package com.pbogdxproject.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Lifecycle {
    void tick(float delta);
    void render(SpriteBatch batch);
    void dispose();
}

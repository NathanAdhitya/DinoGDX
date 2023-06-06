package com.pbogdxproject.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface EntityLifecycle {
    void tick(float delta);
    void render(SpriteBatch batch);
    void dispose();
}

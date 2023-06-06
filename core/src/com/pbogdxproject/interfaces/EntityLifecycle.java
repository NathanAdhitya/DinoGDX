package com.pbogdxproject.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface EntityLifecycle {
    public void tick(float delta);
    public void render(SpriteBatch batch);
    public void dispose();
}

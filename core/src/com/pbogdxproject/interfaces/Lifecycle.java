package com.pbogdxproject.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Lifecycle {
    /**
     * init() should contain code to initialize the object outside the constructor.
     * This function should contain lazily-loaded code that is not required to be run during construction.
     * Place as much code as possible in init() instead of the constructor.
     */
    void init();

    /**
     * tick() should contain code to update the object's state.
     * @param delta The time in seconds since the last tick.
     */
    void tick(float delta);

    /**
     * render() should contain code to draw the object.
     * @param batch The SpriteBatch to draw to.
     */
    void render(SpriteBatch batch);

    /**
     * dispose() should contain code to dispose of the object's resources.
     * This is to clean up any resources that are not automatically cleaned up by the garbage collector.
     * This function should be called when the object is no longer needed.
     * @see <a href="https://libgdx.com/wiki/articles/memory-management/">Memory Management</a>
     */
    void dispose();
}

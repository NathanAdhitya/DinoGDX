package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pbogdxproject.GameState;
import com.pbogdxproject.entities.RectangleCollider;
import com.pbogdxproject.entities.Entity;
import com.pbogdxproject.entities.Player;

abstract public class Obstacle extends Entity {

    public RectangleCollider[] colliders = {};
    private RectangleCollider[] absoluteColliders = {};

    public void init() {
        super.init();

        absoluteColliders = new RectangleCollider[colliders.length];
        for (int i = 0; i < colliders.length; i++) {
            absoluteColliders[i] = colliders[i].copy();
        }
    }

    // Collision Management
    void onPlayerCollision() {
        GameState.onPlayerDeath();
    }

    public void tickCollision(Player p) {
        for (int i = 0; i < colliders.length; i++) {
            RectangleCollider collider = colliders[i];
            RectangleCollider absoluteCollider = absoluteColliders[i];

            absoluteCollider.applyTransformFrom(collider, x, y);
            
            // Check against every player collider
            for (RectangleCollider activeCollider : p.getActiveColliders()) {
                if (absoluteCollider.overlaps(activeCollider)) {
                    onPlayerCollision();
                    return;
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        for (RectangleCollider collider : absoluteColliders) {
            collider.render(batch);
        }
    }

    @Override
    protected void setTexture(String filename) {
        super.setTexture(filename);

        // Recalculate collisions
        recalculateSize();
    }

    @Override
    protected void recalculateSize() {
        super.recalculateSize();

        final float scale = getScale();

        // Recalculate collisions
        for (RectangleCollider collider : colliders) {
            collider.calculateBounds(0, 0, width, height, scale);
        }
    }

    @Override
    public void dispose() {
    }
}

package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pbogdxproject.GameState;
import com.pbogdxproject.entities.Collider2D;
import com.pbogdxproject.entities.Entity;
import com.pbogdxproject.entities.Player;

abstract public class Obstacle extends Entity {

    public Collider2D[] colliders = {};

    // Collision Management
    void onPlayerCollision() {
        GameState.onPlayerDeath();
    }

    public void tickCollision(Player p) {
        for (Collider2D collider : colliders) {
            if (collider.transform(x, y).overlaps(p)) {
                onPlayerCollision();
                return;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        for (Collider2D collider : colliders) {
            collider.transform(x, y).render(batch);
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
        for (Collider2D collider : colliders) {
            collider.calculateBounds(0, 0, width, height, scale);
        }
    }

    @Override
    public void dispose() {
    }
}

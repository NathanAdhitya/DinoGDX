package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pbogdxproject.GameState;
import com.pbogdxproject.entities.Entity;
import com.pbogdxproject.entities.Offset2D;
import com.pbogdxproject.entities.Player;

abstract public class Obstacle extends Entity {
    public Offset2D colliderOffset = new Offset2D();

    /**
     * Relative to the obstacle's position
     */
    protected Rectangle collider;
    private Rectangle absoluteCollider = new Rectangle();


    // Collision Management
    void onPlayerCollision() {
        GameState.onPlayerDeath();
    }

    public void tickCollision(Player p) {
        // Calculate absolute collision box and check for collision
        absoluteCollider.x = collider.x + this.x;
        absoluteCollider.y = collider.y + this.y;

        if (absoluteCollider.overlaps(p)) {
            // Discrete collision checking
            onPlayerCollision();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        // Render collision box
        batch.draw(GameState.hitboxTexture, absoluteCollider.x, absoluteCollider.y, absoluteCollider.width, absoluteCollider.height);
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

        // Recalculate collider offsets
        collider = new Rectangle(
                -colliderOffset.left * getScale(),
                -colliderOffset.bottom * getScale(),
                width + colliderOffset.left + colliderOffset.right * getScale(),
                height + colliderOffset.bottom + colliderOffset.top * getScale()
        );

        absoluteCollider = new Rectangle(
                x + collider.x,
                y + collider.y,
                collider.width,
                collider.height
        );
    }

    @Override
    public void dispose() {
    }
}

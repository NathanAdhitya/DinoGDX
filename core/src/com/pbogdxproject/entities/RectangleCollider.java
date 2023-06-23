package com.pbogdxproject.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pbogdxproject.GameConstants;
import com.pbogdxproject.interfaces.Lifecycle;

import java.util.Random;

public class RectangleCollider extends Rectangle implements Lifecycle {
    final private static Texture texture = new Texture("textures/hitbox.png");
    final private static Sprite sprite = new Sprite(texture);
    final private static Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.PURPLE, Color.CORAL, Color.CYAN, Color.GOLD, Color.MAGENTA, Color.OLIVE, Color.ORANGE, Color.PINK, Color.SKY, Color.TEAL, Color.VIOLET, Color.WHITE};
    final private static Random rnd = new Random();

    protected Offset2D offset;

    protected Color color = colors[rnd.nextInt(colors.length)];

    public RectangleCollider(float x, float y, float width, float height, Offset2D offset) {
        this.offset = offset;
        calculateBounds(x, y, width, height, 1);
    }

    public RectangleCollider(Offset2D offset) {
        this.offset = offset;
    }

    @Override
    public void init() {

    }

    public void tick(float delta) {
    }

    public void render(SpriteBatch batch) {
        if (!GameConstants.DEBUG_DRAW_COLLISION_SHAPES) return;
        sprite.setBounds(x, y, width, height);
        sprite.setColor(color);
        sprite.draw(batch);
    }

    public void dispose() {
    }

    public void calculateBounds(float x, float y, float width, float height, float scale) {
        this.x = x + offset.left * scale;
        this.y = y + offset.bottom * scale;
        this.width = width - (offset.right + offset.left) * scale;
        this.height = height - (offset.top + offset.bottom) * scale;
    }

    public void applyTransformFrom(RectangleCollider collider, float x, float y) {
        this.x = collider.x + x;
        this.y = collider.y + y;
        this.width = collider.width;
        this.height = collider.height;
    }

    public RectangleCollider copy() {
        return new RectangleCollider(x, y, width, height, offset.clone());
    }
}

package com.pbogdxproject.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.interfaces.Lifecycle;

import java.util.Random;

public class Collider2D extends Rectangle implements Lifecycle {
    final private static Texture texture = new Texture("textures/hitbox.png");
    final private static Sprite sprite = new Sprite(texture);
    final private static Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.PURPLE, Color.CORAL, Color.CYAN, Color.GOLD, Color.MAGENTA, Color.OLIVE, Color.ORANGE, Color.PINK, Color.SKY, Color.TEAL, Color.VIOLET, Color.WHITE};
    final private static Random rnd = new Random();

    protected Offset2D offset;

    protected Color color = colors[rnd.nextInt(colors.length)];

    public Collider2D(float x, float y, float width, float height, Offset2D offset) {
        this.offset = offset;
        calculateBounds(x, y, width, height, 1);
    }

    public Collider2D(Offset2D offset){
        this.offset = offset;
    }

    public void init(){}

    public void tick(float delta){}

    public void render(SpriteBatch batch){
        sprite.setBounds(x, y, width, height);
        sprite.setColor(color);
        sprite.draw(batch);
    }

    public void dispose(){}

    public void calculateBounds(float x, float y, float width, float height, float scale){
        this.x = (x + offset.left) * scale;
        this.y = (y + offset.bottom) * scale;
        this.width = width - (offset.right + offset.left) * scale;
        this.height = height - (offset.top + offset.bottom) * scale;
    }

    public Collider2D transform(float x, float y){
        Collider2D clone = new Collider2D(this.x + x, this.y + y, width, height, new Offset2D());
        clone.color = this.color;
        return clone;
    }

    public Collider2D copy() {
        return new Collider2D(x, y, width, height, offset.clone());
    }
}

package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.entities.Collider2D;
import com.pbogdxproject.entities.Offset2D;

import java.util.Arrays;

public class Cactus extends Obstacle {

    final private static Collider2D[] COLLIDERS = {
            new Collider2D(new Offset2D(0,0,15,15)),
            new Collider2D(new Offset2D(20,30,0,0))
    };

    @Override
    public void init() {
        texture = MyGdxGame.assets.get("textures/cactus.png", Texture.class);
        y = 100;

        colliders = COLLIDERS;

        super.setScale(0.75f);
        super.init();
    }
}

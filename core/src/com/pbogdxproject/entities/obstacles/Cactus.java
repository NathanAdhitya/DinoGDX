package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.entities.RectangleCollider;
import com.pbogdxproject.entities.Offset2D;

public class Cactus extends Obstacle {

    final private static RectangleCollider[] COLLIDERS = {
            new RectangleCollider(new Offset2D(0,0,15,15)),
            new RectangleCollider(new Offset2D(20,30,0,0))
    };

    @Override
    public void init() {
        texture = MyGdxGame.assets.get("textures/cactus.png", Texture.class);
        y = 100;

        colliders = COLLIDERS;
        super.init();
    }
}

package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.entities.utils.Obstacle;
import com.pbogdxproject.entities.utils.Offset2D;
import com.pbogdxproject.entities.utils.RectangleCollider;

public class Cart extends Obstacle {
    final private static RectangleCollider[] COLLIDERS = {
            new RectangleCollider(new Offset2D(17, 15, 10, 50)),
            new RectangleCollider(new Offset2D(60, 15, 10, 10)),
            new RectangleCollider(new Offset2D(23, 15, 10, 23))
    };

    @Override
    public void init() {
        texture = MyGdxGame.assets.get("textures/cart.png", Texture.class);
        y = 95;

        colliders = COLLIDERS;
        super.init();
    }
}

package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.entities.utils.Obstacle;
import com.pbogdxproject.entities.utils.Offset2D;
import com.pbogdxproject.entities.utils.RectangleCollider;

public class Cart extends Obstacle {
    final private static RectangleCollider[] COLLIDERS = {
        new RectangleCollider(new Offset2D(16, 15, 10, 20)),
        new RectangleCollider(new Offset2D(50, 20, 10, 1))
    };

    @Override
    public void init() {
        texture = MyGdxGame.assets.get("textures/cart.png", Texture.class);
        y = 95;

        colliders = COLLIDERS;
        super.init();
    }
}

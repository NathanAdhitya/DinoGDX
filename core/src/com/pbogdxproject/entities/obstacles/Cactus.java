package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.pbogdxproject.GameConstants;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.entities.utils.Obstacle;
import com.pbogdxproject.entities.utils.Offset2D;
import com.pbogdxproject.entities.utils.RectangleCollider;

public class Cactus extends Obstacle {

    final private static RectangleCollider[] COLLIDERS = {
        new RectangleCollider(new Offset2D(0, 0, 16, 16)),
        new RectangleCollider(new Offset2D(22, 30, 2, 2))
    };

    @Override
    public void init() {
        texture = MyGdxGame.assets.get("textures/cactus.png", Texture.class);
        y = GameConstants.Y_GROUND;

        colliders = COLLIDERS;
        super.init();
    }
}

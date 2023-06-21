package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.entities.Offset2D;

public class Cactus extends Obstacle {

    @Override
    public void init() {
        texture = MyGdxGame.assets.get("textures/cactus.png", Texture.class);
        y = 100;
        colliderOffset = new Offset2D(0,0,-10,-10);
        super.setScale(0.75f);
        super.init();
    }
}

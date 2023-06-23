package com.pbogdxproject.scenes.worlds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pbogdxproject.entities.obstacles.Cactus;
import com.pbogdxproject.entities.obstacles.Obstacle;
import com.pbogdxproject.interfaces.Lifecycle;

public class WorldNormal extends World {

    @Override
    public void init() {

    }

    @Override
    public void tick(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public Obstacle spawnObstacle() {
        return new Cactus();
    }
}

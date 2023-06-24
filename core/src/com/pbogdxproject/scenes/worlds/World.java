package com.pbogdxproject.scenes.worlds;

import com.pbogdxproject.entities.utils.Obstacle;
import com.pbogdxproject.interfaces.Lifecycle;

abstract public class World implements Lifecycle {
    Lifecycle[] backgroundEntities;
    Obstacle[] obstacles;

    abstract public Obstacle spawnObstacle();
}

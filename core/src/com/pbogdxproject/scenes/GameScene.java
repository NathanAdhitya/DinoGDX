package com.pbogdxproject.scenes;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pbogdxproject.GameState;
import com.pbogdxproject.entities.Player;
import com.pbogdxproject.entities.obstacles.Obstacle;
import com.pbogdxproject.interfaces.Lifecycle;
import com.pbogdxproject.scenes.parts.HighScoreDisplay;
import com.pbogdxproject.scenes.parts.ScoreDisplay;
import com.pbogdxproject.scenes.parts.ScrollingFloor;
import com.pbogdxproject.scenes.worlds.World;
import com.pbogdxproject.scenes.worlds.WorldNormal;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScene implements Lifecycle {
    ArrayList<Lifecycle> lifecycles = new ArrayList<>();
    World currentWorld = new WorldNormal();
    long lastObstacleSpawnTime = TimeUtils.millis();
    float obstacleSpawnInterval = 2f;
    ArrayList<Obstacle> obstacles = new ArrayList<>();
    Player player = new Player();

    Camera camera;
    Viewport viewport;

    public GameScene(Camera camera, Viewport viewport) {
        this.camera = camera;
        this.viewport = viewport;
    }

    @Override
    public void init() {
        // Create Player
        lifecycles.add(currentWorld);
        lifecycles.add(new ScrollingFloor(viewport));

        lifecycles.add(player);
        lifecycles.add(new HighScoreDisplay(viewport));
        lifecycles.add(new ScoreDisplay(viewport));

        // Call init on all lifecycles
        lifecycles.forEach(Lifecycle::init);
    }

    public void tick(float delta) {
        if (GameState.isAlive) {
            lifecycles.forEach(v -> v.tick(delta));
            obstacles.forEach(v -> v.tick(delta));
            obstacles.forEach(v -> v.tickCollision(player));

            // Increment score
            GameState.sessionScore += delta * 5 * GameState.scrollSpeed * GameState.scrollSpeed;

            // Increment scroll speed
            if (GameState.sessionScore > 1000) {
                GameState.scrollSpeed = Math.min(GameState.sessionScore * 0.0015f + 2.5f, 4);
            } else if (GameState.sessionScore > 500) {
                GameState.scrollSpeed = 2.5f;
            } else if (GameState.sessionScore > 250) {
                GameState.scrollSpeed = 2.25f;
            } else if (GameState.sessionScore > 80) {
                GameState.scrollSpeed = 2f;
            }


            // Spawn random obstacle
            if (TimeUtils.millis() - lastObstacleSpawnTime > obstacleSpawnInterval * 1000) {
                lastObstacleSpawnTime = TimeUtils.millis();
                obstacleSpawnInterval = Math.max(obstacleSpawnInterval * 0.95f, 0.5f);

                Obstacle newObstacle = currentWorld.spawnObstacle();
                if (newObstacle != null) {
                    obstacles.add(newObstacle);
                    newObstacle.x = 1000;
                    newObstacle.init();
                    newObstacle.tickCollision(player);
                }
            }

            // Destroy all obstacles outside view
            for (Iterator<Obstacle> iterator = obstacles.iterator(); iterator.hasNext(); ) {
                Obstacle v = iterator.next();
                if (v.x < -128) {
                    v.reset();
                    v.dispose();
                    iterator.remove();
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        lifecycles.forEach(v -> v.render(batch));
        obstacles.forEach(v -> v.render(batch));
    }

    public void dispose() {
        lifecycles.forEach(Lifecycle::dispose);
        obstacles.forEach(Lifecycle::dispose);
    }
}

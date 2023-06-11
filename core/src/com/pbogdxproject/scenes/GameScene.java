package com.pbogdxproject.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.pbogdxproject.GameState;
import com.pbogdxproject.entities.Player;
import com.pbogdxproject.interfaces.Lifecycle;
import com.pbogdxproject.scenes.parts.ScoreDisplay;
import com.pbogdxproject.scenes.parts.ScrollingFloor;
import com.pbogdxproject.scenes.worlds.World;
import com.pbogdxproject.scenes.worlds.WorldNormal;

import java.util.ArrayList;

public class GameScene implements Lifecycle {
    ArrayList<Lifecycle> lifecycles = new ArrayList<>();
    World currentWorld = new WorldNormal();
    long lastObstacleSpawnTime = TimeUtils.millis();
    float obstacleSpawnInterval = 1.5f;

    public GameScene() {
        // Create Player
        lifecycles.add(currentWorld);
        lifecycles.add(new ScrollingFloor());
        lifecycles.add(new Player());
        lifecycles.add(new ScoreDisplay());
    }

    public void tick(float delta) {
        lifecycles.forEach(v -> v.tick(delta));

        // Increment score
        GameState.sessionScore += delta * 10 * GameState.scrollSpeed;

        // Increment scroll speed
        if (GameState.sessionScore > 1000) {
            GameState.scrollSpeed = Math.min(GameState.sessionScore * 0.0015f + 0.5f, 5);
        } else if (GameState.sessionScore > 500) {
            GameState.scrollSpeed = 2f;
        } else if (GameState.sessionScore > 250) {
            GameState.scrollSpeed = 1.5f;
        } else if (GameState.sessionScore > 100) {
            GameState.scrollSpeed = 1.25f;
        } else if (GameState.sessionScore > 50) {
            GameState.scrollSpeed = 1.1f;
        }

    }

    public void render(SpriteBatch batch) {
        lifecycles.forEach(v -> v.render(batch));
    }

    public void dispose() {
        lifecycles.forEach(Lifecycle::dispose);
    }
}

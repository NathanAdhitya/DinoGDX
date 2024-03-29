package com.pbogdxproject.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pbogdxproject.GameConstants;
import com.pbogdxproject.GameState;
import com.pbogdxproject.GameStatus;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.entities.Player;
import com.pbogdxproject.entities.background.Cloud;
import com.pbogdxproject.entities.obstacles.Bird;
import com.pbogdxproject.entities.obstacles.Cactus;
import com.pbogdxproject.entities.obstacles.Cart;
import com.pbogdxproject.entities.obstacles.Snake;
import com.pbogdxproject.entities.utils.BackgroundEntity;
import com.pbogdxproject.entities.utils.Entity;
import com.pbogdxproject.entities.utils.Obstacle;
import com.pbogdxproject.interfaces.Lifecycle;
import com.pbogdxproject.scenes.parts.GameStateDisplay;
import com.pbogdxproject.scenes.parts.HighScoreDisplay;
import com.pbogdxproject.scenes.parts.ScoreDisplay;
import com.pbogdxproject.scenes.parts.ScrollingFloor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

public class GameScene implements Lifecycle {
    ArrayList<Lifecycle> lifecycles = new ArrayList<>();
    long lastObstacleSpawnTime = 0;
    long lastCloudSpawnAttempt = 0;
    float obstacleSpawnInterval = 2f;
    ArrayList<Entity> obstacles = new ArrayList<>();
    ArrayList<BackgroundEntity> backgroundEntities = new ArrayList<>();
    Player player = new Player();

    ScrollingFloor scrollingFloor;
    ScoreDisplay scoreDisplay;

    final Class<?>[] SPAWNABLE_OBSTACLES = {
        Bird.class,
        Cactus.class,
        Cactus.class,
        Cactus.class,
        Cactus.class,
        Cactus.class,
        Cart.class,
        Snake.class
    };

    Camera camera;
    Viewport viewport;
    Music pointSound;

    public GameScene(Camera camera, Viewport viewport) {
        this.camera = camera;
        this.viewport = viewport;
    }

    public void init() {
        // Create Player
        scrollingFloor = new ScrollingFloor(viewport);
        lifecycles.add(scrollingFloor);

        lifecycles.add(player);
        lifecycles.add(new HighScoreDisplay(viewport));

        scoreDisplay = new ScoreDisplay(viewport);
        lifecycles.add(scoreDisplay);

        lifecycles.add(new GameStateDisplay(viewport));

        // Call init on all lifecycles
        lifecycles.forEach(Lifecycle::init);

        // Tick the player once.
        player.tick(0);

        pointSound = MyGdxGame.assets.get("sounds/point.wav", Music.class);
    }

    public void tick(float delta) {
        // Process GameState based on jump button.
        boolean jumpButtonPressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        if (jumpButtonPressed) {
            if (GameState.status == GameStatus.STOPPED) {
                GameState.status = GameStatus.PLAYING;
                GameState.scrollSpeed = GameConstants.INITIAL_SCROLL_SPEED;

                scrollingFloor.runInitialAnimation();

                // Delay initial obstacle and cloud spawn by TIME_TO_FULL_GROUND_ANIMATION
                lastObstacleSpawnTime =
                    TimeUtils.millis() + (long) GameConstants.TIME_TO_FULL_GROUND_ANIMATION * 1000 - (long) obstacleSpawnInterval * 1000;
                lastCloudSpawnAttempt =
                    TimeUtils.millis() + (long) GameConstants.TIME_TO_FULL_GROUND_ANIMATION * 1000 - (long) obstacleSpawnInterval * 1000;

            } else if (GameState.status == GameStatus.DEAD && TimeUtils.timeSinceMillis(GameState.lastDeath) > 1000) {
                GameState.status = GameStatus.PLAYING;
                GameState.scrollSpeed = GameConstants.INITIAL_SCROLL_SPEED;

                // Reset current score
                GameState.sessionScore = 0;

                // Return player x to initial
                player.y = 100;

                // Return obstacleSpawnInterval to initial
                obstacleSpawnInterval = 2f;


                // Dispose all on-screen entities and obstacles
                obstacles.forEach(Lifecycle::dispose);
                obstacles.clear();
                backgroundEntities.forEach(Lifecycle::dispose);
                backgroundEntities.clear();

                scrollingFloor.runInitialAnimation();

                // Delay initial obstacle and cloud spawn by TIME_TO_FULL_GROUND_ANIMATION
                lastObstacleSpawnTime =
                    TimeUtils.millis() + (long) GameConstants.TIME_TO_FULL_GROUND_ANIMATION * 1000 - (long) obstacleSpawnInterval * 1000;
                lastCloudSpawnAttempt =
                    TimeUtils.millis() + (long) GameConstants.TIME_TO_FULL_GROUND_ANIMATION * 1000 - (long) obstacleSpawnInterval * 1000;
            }
        }


        if (GameState.status == GameStatus.PLAYING) {
            lifecycles.forEach(v -> v.tick(delta));
            obstacles.forEach(v -> v.tick(delta));
            obstacles.forEach(v -> {
                if (v instanceof Obstacle) ((Obstacle) v).tickCollision(player);
            });
            backgroundEntities.forEach(v -> v.tick(delta));

            // Increment score
            float scoreDelta = delta * 5 * GameState.scrollSpeed;

            // Check whether score passed 100, if yes, then flash score display and play point sound
            if ((int) (GameState.sessionScore / 100) < (int) (GameState.sessionScore + scoreDelta) / 100) {
                pointSound.play();
                scoreDisplay.flash();
            }

            GameState.sessionScore += scoreDelta;

            // Increment scroll speed
            if (GameState.sessionScore > 1000) {
                GameState.scrollSpeed = Math.min(GameState.sessionScore * 0.0015f + 2.5f, 5);
            } else if (GameState.sessionScore > 500) {
                GameState.scrollSpeed = 3.25f;
            } else if (GameState.sessionScore > 250) {
                GameState.scrollSpeed = 3f;
            } else if (GameState.sessionScore > 80) {
                GameState.scrollSpeed = 2.75f;
            }


            // Spawn random obstacle
            if (TimeUtils.millis() - lastObstacleSpawnTime > obstacleSpawnInterval * 1000) {
                lastObstacleSpawnTime = TimeUtils.millis();

                // Reduce obstacle spawn time
                obstacleSpawnInterval = Math.max(obstacleSpawnInterval * 0.95f, 0.75f);

                // Randomly decide whether obstacle should be spawned or not
                if (GameState.RANDOM.nextFloat() > 0.2f) {
                    try {
                        Obstacle newObstacle =
                            (Obstacle) SPAWNABLE_OBSTACLES[GameState.RANDOM.nextInt(SPAWNABLE_OBSTACLES.length)].getDeclaredConstructor().newInstance();

                        obstacles.add(newObstacle);
                        newObstacle.x = viewport.getWorldWidth() + 100;
                        newObstacle.init();
                        newObstacle.tickCollision(player);

                        // If the spawnedObstacle is a cactus, then randomly stack 1-3 cactuses on next to it.
                        if (newObstacle instanceof Cactus) {
                            if (GameState.RANDOM.nextFloat() > 0.5f) {
                                Obstacle newObstacle2 = new Cactus();
                                obstacles.add(newObstacle2);
                                newObstacle2.x = newObstacle.x + newObstacle.getWidth();
                                newObstacle2.init();
                                newObstacle2.tickCollision(player);

                                if (GameState.RANDOM.nextFloat() > 0.5f) {
                                    Obstacle newObstacle3 = new Cactus();
                                    obstacles.add(newObstacle3);
                                    newObstacle3.x = newObstacle2.x + newObstacle2.getWidth();
                                    newObstacle3.init();
                                    newObstacle3.tickCollision(player);
                                }
                            }
                        }
                    } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                             NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Spawn clouds randomly
            if (TimeUtils.millis() - lastCloudSpawnAttempt > 400 / GameState.scrollSpeed) {
                lastCloudSpawnAttempt = TimeUtils.millis();
                if (GameState.RANDOM.nextFloat() < 0.2f) {
                    Cloud newCloud = new Cloud();
                    backgroundEntities.add(newCloud);
                    newCloud.x = viewport.getWorldWidth() + 100;
                    newCloud.init();
                }
            }

            // Destroy all obstacles and background entities outside view
            for (Iterator<BackgroundEntity> iterator = backgroundEntities.iterator(); iterator.hasNext(); ) {
                BackgroundEntity v = iterator.next();
                if (v.x < -128) {
                    v.reset();
                    v.dispose();
                    iterator.remove();
                }
            }

            for (Iterator<Entity> iterator = obstacles.iterator(); iterator.hasNext(); ) {
                Entity v = iterator.next();
                if (v.x < -128) {
                    v.reset();
                    v.dispose();
                    iterator.remove();
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        backgroundEntities.forEach(v -> v.render(batch));
        lifecycles.forEach(v -> v.render(batch));
        obstacles.forEach(v -> v.render(batch));
    }

    public void dispose() {
        lifecycles.forEach(Lifecycle::dispose);
        obstacles.forEach(Lifecycle::dispose);
    }
}

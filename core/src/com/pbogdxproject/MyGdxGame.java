package com.pbogdxproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pbogdxproject.scenes.GameScene;

import java.util.Arrays;

public class MyGdxGame extends ApplicationAdapter {
    public static AssetManager assets = new AssetManager();
    public Music jump, score;
    GameScene gameScene;
    private SpriteBatch batch;
    private Viewport viewport;
    private Viewport startViewport;
    private OrthographicCamera camera;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(
            GameConstants.SCREEN_WIDTH_METERS * GameConstants.METERS_TO_PIXELS_MULTIPLIER,
            GameConstants.SCREEN_HEIGHT_METERS * GameConstants.METERS_TO_PIXELS_MULTIPLIER,
            camera
        );
        startViewport = new ScreenViewport(camera);

        gameScene = new GameScene(camera, viewport);
        batch = new SpriteBatch();

        // Load all assets in assets/textures
        Arrays.stream(Gdx.files.internal("textures").list()).forEach(file -> {
            System.out.println("Loading " + file.path());
            assets.load(file.path(), Texture.class);
            System.out.println(TimeUtils.millis());
        });

        // Load all sounds in assets
        Arrays.stream(Gdx.files.internal("sounds").list()).forEach(file -> {
            System.out.println("Loading " + file.path());
            assets.load(file.path(), Music.class);
        });

        // Ensure all assets have been loaded before initializing the game scene
        assets.finishLoading();

        // Load the current high score
        Preferences preferences = Gdx.app.getPreferences("GameState");
        GameState.highScore = preferences.getInteger("highScore", 0);

        gameScene.init();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        if (!assets.update()) return;

        ScreenUtils.clear((float) 230 / 255, (float) 230 / 255, (float) 230 / 255, 1);
        batch.setProjectionMatrix(camera.combined);
        viewport.apply();

        float delta = Gdx.graphics.getDeltaTime();

        gameScene.tick(delta);

        batch.begin();
        gameScene.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        gameScene.dispose();
    }
}

package com.pbogdxproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pbogdxproject.scenes.GameScene;

import java.util.Arrays;

public class MyGdxGame extends ApplicationAdapter {
    private OrthographicCamera camera;
    public static AssetManager assets = new AssetManager();
    private SpriteBatch batch;
    GameScene gameScene;

    public float screenHeightMeters = 5;
    public float screenWidthMeters = 20;
    public Music jump, score;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        gameScene = new GameScene();
        batch = new SpriteBatch();

        // Load all assets in assets/textures
        Arrays.stream(Gdx.files.internal("textures").list()).forEach(file -> {
            System.out.println("Loading " + file.path());
            assets.load(file.path(), Texture.class);
        });
        // Load all sounds in assets
        jump = Gdx.audio.newMusic(Gdx.files.internal("sound/jump.wav"));
        score = Gdx.audio.newMusic(Gdx.files.internal("sound/point.wav"));

        assets.finishLoading();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.setToOrtho(false, width, height);
    }

    // TODO: Create a viewport to handle the world and render the score seperately.
    @Override
    public void render() {
        if (!assets.update()) return;

        ScreenUtils.clear((float) 230 / 255, (float) 230 / 255, (float) 230 / 255, 1);
        batch.setProjectionMatrix(camera.combined);

        float delta = Gdx.graphics.getDeltaTime();
        gameScene.tick(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            jump.play();
        }

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

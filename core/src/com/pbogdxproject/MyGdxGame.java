package com.pbogdxproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pbogdxproject.scenes.Scene;

public class MyGdxGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	Scene scene;

	public float screenHeightMeters = 5;
	public float screenWidthMeters = 20;


	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		scene = new Scene();
		batch = new SpriteBatch();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		camera.setToOrtho(false, width, height);
	}

	@Override
	public void render () {
		ScreenUtils.clear((float) 230 /255, (float) 230 /255, (float) 230 /255, 1);
		batch.setProjectionMatrix(camera.combined);

		float delta = Gdx.graphics.getDeltaTime();
		scene.tick(delta);

		batch.begin();
		scene.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		scene.dispose();
	}
}

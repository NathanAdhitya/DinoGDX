package com.pbogdxproject.entities.background;

import com.badlogic.gdx.graphics.Texture;
import com.pbogdxproject.GameConstants;
import com.pbogdxproject.GameState;
import com.pbogdxproject.MyGdxGame;

public class Cloud extends BackgroundEntity {
    @Override
    public void init() {
        texture = MyGdxGame.assets.get("textures/cloud.png", Texture.class);

        // Random y
        y = GameState.RANDOM.nextFloat() * 50 + 250;

        // Random scale from 0.25 to 1
        setScale(GameState.RANDOM.nextFloat() * 0.25f + 0.75f);

        // Random speed
        speed = GameState.RANDOM.nextFloat() * 10 + 100;


        super.init();
    }

    @Override
    public void dispose() {

    }

}

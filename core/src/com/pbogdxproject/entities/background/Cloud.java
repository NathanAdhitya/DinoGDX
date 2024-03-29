package com.pbogdxproject.entities.background;

import com.badlogic.gdx.graphics.Texture;
import com.pbogdxproject.GameState;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.entities.utils.BackgroundEntity;

public class Cloud extends BackgroundEntity {
    @Override
    public void init() {
        texture = MyGdxGame.assets.get("textures/cloud.png", Texture.class);

        // Random y
        y = GameState.RANDOM.nextFloat() * 50 + 225;

        // Random scale from 0.75 to 1
        setScale(GameState.RANDOM.nextFloat() * 0.25f + 0.75f);

        // Random speed
        speed = 100;


        super.init();
    }

    @Override
    public void dispose() {

    }

}

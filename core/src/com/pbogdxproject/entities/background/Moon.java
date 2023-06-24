package com.pbogdxproject.entities.background;

import com.badlogic.gdx.graphics.Texture;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.entities.utils.BackgroundEntity;

public class Moon extends BackgroundEntity {
    @Override
    public void init() {
        texture = MyGdxGame.assets.get("textures/moon.png", Texture.class);
        speed = 10;
    }

    @Override
    public void dispose() {

    }
}

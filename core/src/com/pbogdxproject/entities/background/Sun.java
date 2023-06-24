package com.pbogdxproject.entities.background;

import com.badlogic.gdx.graphics.Texture;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.entities.utils.BackgroundEntity;

public class Sun extends BackgroundEntity {
    @Override
    public void init() {
        texture = MyGdxGame.assets.get("textures/sun.png", Texture.class);
        speed = 10;
    }

    @Override
    public void dispose() {

    }
}

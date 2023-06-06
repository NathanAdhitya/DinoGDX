package com.pbogdxproject.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pbogdxproject.interfaces.EntityLifecycle;

public class Player extends Rectangle implements EntityLifecycle {
    Texture texture = new Texture(Gdx.files.internal("t-rex.png"));
    Sprite sprite = new Sprite(texture);

    final static float gravity = 980f;
    float yVelocity = 0;

    float yLowerBound = 100;

    public Player(){
        width = 80;
        height = 86;
        x = 100;
    }
    public void tick(float delta){
        physicsTick(delta);
    }

    private void physicsTick(float dt){
        yVelocity += -gravity * dt;
        y += yVelocity * dt;

        if(y <= yLowerBound) {
            yVelocity = 0;
            y = yLowerBound;

            // Eligible for jumping
            if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                yVelocity = 300;
            }
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(sprite, x, y);
    }

    public void dispose(){
        texture.dispose();
    }
}

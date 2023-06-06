package com.pbogdxproject.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pbogdxproject.entities.Player;
import com.pbogdxproject.interfaces.EntityLifecycle;

import java.util.ArrayList;

public class Scene implements EntityLifecycle {
    ArrayList<EntityLifecycle> lifecycles = new ArrayList<>();

    public Scene(){
        // Create Player
        lifecycles.add(new Player());
    }

    public void tick(float delta){
        lifecycles.forEach(v -> v.tick(delta));
    }

    public void render(SpriteBatch batch){
        lifecycles.forEach(v -> v.render(batch));
    }

    public void dispose(){
        lifecycles.forEach(EntityLifecycle::dispose);
    }
}

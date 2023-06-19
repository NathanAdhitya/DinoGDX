package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pbogdxproject.entities.Entities;
import com.pbogdxproject.interfaces.Lifecycle;

abstract public class Obstacle extends Entities {
   protected Texture image;

   public void setImage(String imageFile) {
      this.image = new Texture(Gdx.files.internal(imageFile));
   }

   @Override
   public void render(SpriteBatch batch) {
      super.render(batch);
   }
}

package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cart extends Obstacle{
   @Override
   public void init() {
      texture = new Texture("textures/cart.png");
      width = 85;
      height = 85;
      x = 100;
   }
}

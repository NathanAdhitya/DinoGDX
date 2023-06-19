package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.graphics.Texture;

public class Cart extends Obstacle{
   Texture image;
   int width;

   int height;
   int x;
   public Cart() {
      width = 85;
      height = 85;
      image = new Texture("cart.png");
      x = 100;
   }
}

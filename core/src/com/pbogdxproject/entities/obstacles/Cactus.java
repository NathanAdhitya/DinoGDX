package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.graphics.Texture;

public class Cactus extends Obstacle{
   Texture image;
   int width;

   int height;
   int x;
   public Cactus() {
      width = 50;
      height = 100;
      image = new Texture("cactus.png");
      x = 100;
   }
}

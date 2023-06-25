package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.entities.utils.Obstacle;
import com.pbogdxproject.entities.utils.Offset2D;
import com.pbogdxproject.entities.utils.RectangleCollider;

public class Snake extends Obstacle {
   final private static RectangleCollider[] COLLIDERS = {
           new RectangleCollider(new Offset2D(0, 5, 18, 15)),
           new RectangleCollider(new Offset2D(40, 10, 10, 4))
   };

   @Override
   public void init() {
      texture = MyGdxGame.assets.get("textures/snake.png", Texture.class);
      y = 100;

      colliders = COLLIDERS;
      super.init();
   }
}

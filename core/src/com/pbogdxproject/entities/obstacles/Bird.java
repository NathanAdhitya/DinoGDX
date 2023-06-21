package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public class Bird extends Obstacle {
   int width;
   int height;
   int [] yValues = {1, 10}; // diisi sama tinggi e burung e mau berapa

   int yValue;
   public Bird() {
      width = 60;
      height = 54;

      Random rndm = new Random();
      this.yValue = this.yValues[rndm.nextInt(this.yValues.length)];

      Texture runningAnimationSheet = new Texture(Gdx.files.internal("textures/bird-anim.png"));

      TextureRegion[][] tmp = TextureRegion.split(runningAnimationSheet,
              runningAnimationSheet.getWidth() / 2
              , runningAnimationSheet.getHeight());

      TextureRegion[] runningFrames = new TextureRegion[2];
      for (int i = 1, n = 0; i < tmp[0].length; i++) {
         runningFrames[n++] = tmp[0][i];
      }
   }

   @Override
   public void init() {

   }

   @Override
   public void tick(float delta) {

   }

   @Override
   public void render(SpriteBatch batch) {

   }

   @Override
   public void dispose() {

   }

   @Override
   void onPlayerCollision() {

   }
}

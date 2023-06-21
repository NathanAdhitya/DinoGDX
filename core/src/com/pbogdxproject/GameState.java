package com.pbogdxproject;

import com.badlogic.gdx.graphics.Texture;

public class GameState {
    public static float scrollSpeed = 1.25f;
    public static float sessionScore = 0;
    public static float highScore = 100;
    public static boolean renderHitboxes = false;
    public static Texture hitboxTexture = new Texture("textures/hitbox.png");

    final public static float physicsScale = 250f;

    public static boolean isAlive = true;

    public static void onPlayerDeath(){
        scrollSpeed = 0;
        isAlive = false;
        System.out.println("Player died!");
    }
}

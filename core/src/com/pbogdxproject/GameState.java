package com.pbogdxproject;

public class GameState {
    public static float scrollSpeed = 1.75f;
    public static float sessionScore = 0;
    public static float highScore = 100;
    public static boolean isAlive = true;

    public static void onPlayerDeath() {
        scrollSpeed = 0;
        isAlive = false;
        System.out.println("Player died!");
    }
}

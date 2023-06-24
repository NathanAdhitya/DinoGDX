package com.pbogdxproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Random;

public class GameState {

    public static Random RANDOM = new Random();

    public static float scrollSpeed = 1.75f;
    public static float sessionScore = 0;
    public static float highScore = 100;
    public static boolean isAlive = true;

    public static void onPlayerDeath() {
        scrollSpeed = 0;
        isAlive = false;
        System.out.println("Player died!");

        // Save high score if it's higher than the current score
        Preferences preferences = Gdx.app.getPreferences("GameState");
        if (sessionScore > preferences.getInteger("highScore", 0)) {
            preferences.putInteger("highScore", (int) sessionScore);
            preferences.flush();

            highScore = sessionScore;
        }
    }


}

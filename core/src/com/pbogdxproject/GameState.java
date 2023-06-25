package com.pbogdxproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;

import java.util.Random;

public class GameState {

    public static Random RANDOM = new Random();

    public static GameStatus status = GameStatus.STOPPED;

    public static float scrollSpeed = 0;
    public static float sessionScore = 0;
    public static float highScore = 100;

    public static Music deadSound = MyGdxGame.assets.get("sounds/die.wav", Music.class);;

    public static void onPlayerDeath() {
        scrollSpeed = 0;
        deadSound.play();
        status = GameStatus.DEAD;
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

package com.pbogdxproject;

public class GameConstants {

    // Screen Settings
    final static public int SCREEN_HEIGHT_METERS = 5;
    final static public int SCREEN_WIDTH_METERS = 12;
    final static public int METERS_TO_PIXELS_MULTIPLIER = 100;
    final static public int Y_GROUND = 1 * METERS_TO_PIXELS_MULTIPLIER;

    // Scene Settings
    final static public float TIME_TO_FULL_GROUND_ANIMATION = 2f;
    final static public float INITIAL_SCROLL_SPEED = 2.5f;

    // Physics Settings
    final public static float PHYSICS_MULTIPLIER = 250f;

    // Debug Settings
    final static public boolean DEBUG_DRAW_COLLISION_SHAPES = false;
}

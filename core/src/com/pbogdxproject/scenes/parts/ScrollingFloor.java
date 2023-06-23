package com.pbogdxproject.scenes.parts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pbogdxproject.GameState;
import com.pbogdxproject.interfaces.Lifecycle;

import java.util.LinkedList;
import java.util.Random;

public class ScrollingFloor implements Lifecycle {

    private Texture texture = new Texture(Gdx.files.internal("textures/ground.png"));
    private TextureRegion[] regions;
    private float currentOffset = 0;
    private float lastCullOffset = 0;
    private LinkedList<Integer> currentPlacedRegions = new LinkedList<>();
    private float startY = 100;
    private float startX = 0;

    private Random rnd = new Random();

    public ScrollingFloor() {
        // Chop up regions based on width to several parts for randomization.
        // Chop up to 8 regions.
        int width = texture.getWidth();
        int height = texture.getHeight();
        regions = new TextureRegion[16];
        for (int i = 0; i < 16; i++) {
            regions[i] = new TextureRegion(texture, width / 16 * i, 0, width / 16, height);
        }

        // Calculate startY
        startY -= 4;

        // For Testing
        for (int i = 0; i < 16; i++) {
            currentPlacedRegions.add(rnd.nextInt(16));
        }
    }

    @Override
    public void tick(float delta) {
        // Update the scrolling floor (move, generate next.)
        currentOffset += delta * 200 * GameState.scrollSpeed;
    }

    @Override
    public void render(SpriteBatch batch) {
        int width = 48;
        int height = 32;

        float localOffset = currentOffset % (width);

        if((currentOffset - lastCullOffset) >= width){
            int cullCount = (int) ((currentOffset - lastCullOffset) / width);
            lastCullOffset += cullCount * width;
            for (int i = 0; i < cullCount; i++) {
                currentPlacedRegions.removeFirst();
                currentPlacedRegions.add(rnd.nextInt(16));
            }
        }



        // Render all the ground
        for (int i = 0; i < currentPlacedRegions.size(); i++) {
            batch.draw(
                regions[currentPlacedRegions.get(i)],
                startX + width * i - localOffset,
                startY,
                width,
                height
            );
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}

package com.pbogdxproject.scenes.parts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pbogdxproject.GameState;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.interfaces.Lifecycle;

import java.util.LinkedList;
import java.util.Random;

public class ScrollingFloor implements Lifecycle {

    /**
     * Indicates how many slices does the ground texture have.
     */
    private final static int REGION_SLICES = 16;
    Viewport viewport;
    private Texture texture;
    private TextureRegion[] slicedRegions;
    private float currentOffset = 0;
    private float lastCullOffset = 0;
    private LinkedList<Integer> currentPlacedRegions = new LinkedList<>();
    private float startY = 100;
    private float startX = 0;
    private Random rnd = new Random();

    public ScrollingFloor(Viewport viewport) {
        this.viewport = viewport;
    }

    @Override
    public void init() {
        texture = MyGdxGame.assets.get("textures/ground.png", Texture.class);

        // Chop up regions based on width to several parts for randomization.
        int width = texture.getWidth();
        int height = texture.getHeight();
        slicedRegions = new TextureRegion[REGION_SLICES];

        for (int i = 0; i < REGION_SLICES; i++) {
            slicedRegions[i] = new TextureRegion(texture, width / REGION_SLICES * i, 0, width / REGION_SLICES, height);
        }

        // Calculate startY
        startY -= 4;

        for (int i = 0; i < 16; i++) {
            currentPlacedRegions.add(rnd.nextInt(REGION_SLICES));
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

        if ((currentOffset - lastCullOffset) >= width) {
            int cullCount = (int) ((currentOffset - lastCullOffset) / width);
            lastCullOffset += cullCount * width;
            for (int i = 0; i < cullCount; i++) {
                currentPlacedRegions.removeFirst();
                currentPlacedRegions.add(rnd.nextInt(REGION_SLICES));
            }
        }


        // Render all the ground
        for (int i = 0; i < currentPlacedRegions.size(); i++) {
            batch.draw(
                slicedRegions[currentPlacedRegions.get(i)],
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

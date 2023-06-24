package com.pbogdxproject.scenes.parts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pbogdxproject.GameConstants;
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
    final private int RENDER_REGION_MULT = 2;
    Viewport viewport;
    private Texture texture;
    private TextureRegion[] slicedRegions;
    private float currentOffset = 0;
    private float lastCullOffset = 0;
    private LinkedList<Integer> currentPlacedRegions = new LinkedList<>();
    private float startY = GameConstants.Y_GROUND;
    private float startX = 0;
    private Random rnd = new Random();
    private float initialAnimationStateTime = 0;
    private int renderRegionWidth;
    private int renderRegionHeight;


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
        startY -= 8;

        // Calculate needed slices to fill the screen
        final int requiredSlices = (int) (viewport.getWorldWidth() / (width / REGION_SLICES) + 2);

        for (int i = 0; i < requiredSlices; i++) {
            currentPlacedRegions.add(rnd.nextInt(REGION_SLICES));
        }

        renderRegionWidth = (texture.getWidth() / REGION_SLICES) * RENDER_REGION_MULT;
        renderRegionHeight = texture.getHeight() * RENDER_REGION_MULT;
    }

    public void runInitialAnimation() {
        initialAnimationStateTime = 0;
    }

    @Override
    public void tick(float delta) {
        // Update the scrolling floor (move, generate next.)
        currentOffset += delta * 200 * GameState.scrollSpeed;
        initialAnimationStateTime += delta;
    }

    @Override
    public void render(SpriteBatch batch) {

        float localOffset = currentOffset % (renderRegionWidth);

        if ((currentOffset - lastCullOffset) >= renderRegionWidth) {
            int cullCount = (int) ((currentOffset - lastCullOffset) / renderRegionWidth);
            lastCullOffset += cullCount * renderRegionWidth;
            for (int i = 0; i < cullCount; i++) {
                currentPlacedRegions.removeFirst();
                currentPlacedRegions.add(rnd.nextInt(REGION_SLICES));
            }
        }


        // Render all the ground until the x position is reached.
        float worldWidth = viewport.getWorldWidth();
        float xLimit =
            Math.min(initialAnimationStateTime / GameConstants.TIME_TO_FULL_GROUND_ANIMATION, 1f) * worldWidth;

        for (int i = 0; i < currentPlacedRegions.size(); i++) {
            float regionX = startX + renderRegionWidth * i - localOffset;

            if (regionX > xLimit) {
                break;
            }

            TextureRegion targetRegion = slicedRegions[currentPlacedRegions.get(i)];

            // If the region is partially outside the screen, cut it.
            if (regionX + renderRegionWidth > xLimit) {
                int originalWidth = targetRegion.getRegionWidth();

                targetRegion = new TextureRegion(
                    targetRegion,
                    0,
                    0,
                    (int) (xLimit - regionX) / RENDER_REGION_MULT,
                    targetRegion.getRegionHeight()
                );

                batch.draw(
                    targetRegion,
                    regionX,
                    startY,
                    (xLimit - regionX),
                    renderRegionHeight
                );
            } else {
                batch.draw(
                    targetRegion,
                    regionX,
                    startY,
                    renderRegionWidth,
                    renderRegionHeight
                );
            }
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}

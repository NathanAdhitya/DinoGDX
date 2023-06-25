package com.pbogdxproject.entities.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pbogdxproject.GameConstants;
import com.pbogdxproject.MyGdxGame;
import com.pbogdxproject.entities.utils.Obstacle;
import com.pbogdxproject.entities.utils.Offset2D;
import com.pbogdxproject.entities.utils.RectangleCollider;

public class Cactus extends Obstacle {

    final private static RectangleCollider[] BIG_CACTUS_COLLIDERS = {
        new RectangleCollider(new Offset2D(0, 0, 16, 16)),
        new RectangleCollider(new Offset2D(22, 30, 2, 2))
    };

    final private static RectangleCollider[] SMALL_CACTUS_COLLIDERS = {
        new RectangleCollider(new Offset2D(0, 0, 12, 12)),
        new RectangleCollider(new Offset2D(22, 30, 2, 2))
    };

    private Texture textures;
    private TextureRegion chosenTexture;

    @Override
    public void init() {
        boolean isSmall = Math.random() < 0.5;

        // Fill in texture for width and height calculation.
        if (isSmall) {
            texture = MyGdxGame.assets.get("textures/small-cactus.png", Texture.class);
            textures = MyGdxGame.assets.get("textures/small-cactuses.png", Texture.class);
            colliders = SMALL_CACTUS_COLLIDERS;
        } else {
            texture = MyGdxGame.assets.get("textures/big-cactus.png", Texture.class);
            textures = MyGdxGame.assets.get("textures/big-cactuses.png", Texture.class);
            colliders = BIG_CACTUS_COLLIDERS;
        }

        TextureRegion[] regions;
        if (isSmall) {
            // Split texture into 6 regions.
            regions = TextureRegion.split(textures, textures.getWidth() / 6, textures.getHeight())[0];
        } else {
            // Split texture into 4 regions.
            regions = TextureRegion.split(textures, textures.getWidth() / 4, textures.getHeight())[0];
        }
        chosenTexture = regions[(int) (Math.random() * regions.length)];

        y = GameConstants.Y_GROUND;
        super.init();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(chosenTexture, x, y);

        renderColliders(batch);
    }
}

package com.pbogdxproject.scenes.parts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pbogdxproject.GameState;
import com.pbogdxproject.GameStatus;
import com.pbogdxproject.interfaces.Lifecycle;

public class GameStateDisplay implements Lifecycle {
    final static int posTop = 120;
    final static int posRight = 80;

    FreeTypeFontGenerator fontGenerator;
    BitmapFont font;
    GlyphLayout glyphLayout = new GlyphLayout();

    Viewport viewport;

    public GameStateDisplay(Viewport viewport) {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 16;
        fontParameter.color = new Color(0x737373ff);
        font = fontGenerator.generateFont(fontParameter);

        this.viewport = viewport;
    }

    @Override
    public void init() {

    }

    @Override
    public void tick(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        // TODO: This can be made to only run during window resize.
        // Calculate the position of the display
        int x = (int) (viewport.getWorldWidth() - posRight);
        int y = (int) (viewport.getWorldHeight() - posTop);

        String text = "";

        switch (GameState.status) {
            case DEAD -> text = "YOU DIED! PRESS SPACE TO RETRY!";
            case STOPPED -> text = "PRESS SPACE TO START!";
        }

        glyphLayout.setText(font, text);
        x -= glyphLayout.width;

        // Draw the display
        font.draw(batch, text, x, y);
    }

    @Override
    public void dispose() {
        fontGenerator.dispose();
        font.dispose();
    }
}

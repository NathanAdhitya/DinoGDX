package com.pbogdxproject.scenes.parts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.pbogdxproject.GameState;
import com.pbogdxproject.interfaces.Lifecycle;

public class ScoreDisplay implements Lifecycle {

    final static int scorePad = 8;
    final static int posTop = 80;
    final static int posRight = 80;

    FreeTypeFontGenerator fontGenerator;
    BitmapFont font;
    GlyphLayout glyphLayout = new GlyphLayout();

    public ScoreDisplay() {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 16;
        fontParameter.color = new Color(0x535353ff);
        font = fontGenerator.generateFont(fontParameter);
    }

    @Override
    public void tick(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        // TODO: This can be made to only run during window resize.
        // Calculate the position of the score display
        int x = Gdx.graphics.getWidth() - posRight;
        int y = Gdx.graphics.getHeight() - posTop;


        String scoreText = String.format("%1$0"+scorePad+".0f", GameState.sessionScore);
        glyphLayout.setText(font, scoreText);
        x -= glyphLayout.width;

        // Draw the score display
        font.draw(batch, scoreText, x, y);
    }

    @Override
    public void dispose() {
        fontGenerator.dispose();
        font.dispose();
    }
}

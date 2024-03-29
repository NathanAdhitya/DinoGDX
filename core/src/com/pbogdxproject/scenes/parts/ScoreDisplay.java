package com.pbogdxproject.scenes.parts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pbogdxproject.GameState;
import com.pbogdxproject.interfaces.Lifecycle;

public class ScoreDisplay implements Lifecycle {

    final static int scorePad = 5;
    final static int posTop = 80;
    final static int posRight = 80;

    final static long flashDurationMs = 1500;
    final static long flashPeriodMs = 250;
    private long lastFlastRequest = 0;
    private long flashRequestValue = 0;

    FreeTypeFontGenerator fontGenerator;
    BitmapFont font;
    GlyphLayout glyphLayout = new GlyphLayout();

    Viewport viewport;


    public ScoreDisplay(Viewport viewport) {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 16;
        fontParameter.color = new Color(0x535353ff);
        font = fontGenerator.generateFont(fontParameter);

        this.viewport = viewport;
    }

    public void flash() {
        lastFlastRequest = TimeUtils.millis();
        flashRequestValue = ((long) GameState.sessionScore / 100 + 1) * 100;
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
        // Calculate the position of the score display
        int x = (int) (viewport.getWorldWidth() - posRight);
        int y = (int) (viewport.getWorldHeight() - posTop);

        // Calculate the score text
        float scoreValue = TimeUtils.timeSinceMillis(lastFlastRequest) < flashDurationMs ? flashRequestValue : GameState.sessionScore;

        String scoreText = String.format("%1$0" + scorePad + ".0f", scoreValue);
        glyphLayout.setText(font, scoreText);
        x -= glyphLayout.width;

        // Draw the score display
        if (TimeUtils.timeSinceMillis(lastFlastRequest) < flashDurationMs) {
            if (TimeUtils.timeSinceMillis(lastFlastRequest) % (flashPeriodMs * 2) < flashPeriodMs) {
            } else {
                font.draw(batch, scoreText, x, y);
            }
        } else {
            font.draw(batch, scoreText, x, y);
        }
    }

    @Override
    public void dispose() {
        fontGenerator.dispose();
        font.dispose();
    }
}

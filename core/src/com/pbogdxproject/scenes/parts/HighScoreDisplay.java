package com.pbogdxproject.scenes.parts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.pbogdxproject.GameState;
import com.pbogdxproject.interfaces.Lifecycle;

public class HighScoreDisplay implements Lifecycle {

    final static int scorePad = 5;
    final static int posTop = 80;
    final static int posRight = 180;

    FreeTypeFontGenerator fontGenerator;
    BitmapFont font;
    GlyphLayout glyphLayout = new GlyphLayout();

    public HighScoreDisplay() {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 16;
        fontParameter.color = new Color(0x737373ff);
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

        if(GameState.highScore != 0){
            String scoreText = String.format("HI %1$0"+scorePad+".0f", GameState.highScore);
            glyphLayout.setText(font, scoreText);
            x -= glyphLayout.width;

            // Draw the score display
            font.draw(batch, scoreText, x, y);
        }
    }

    @Override
    public void dispose() {
        fontGenerator.dispose();
        font.dispose();
    }
}

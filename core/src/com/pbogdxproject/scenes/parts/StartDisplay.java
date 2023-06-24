package com.pbogdxproject.scenes.parts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.pbogdxproject.interfaces.Lifecycle;

import java.util.ArrayList;

public class StartDisplay implements Lifecycle {

    // "No internet" page.
    ArrayList<Lifecycle> lifecycles = new ArrayList<>();


    FreeTypeFontGenerator fontGenerator;
    BitmapFont font;
    GlyphLayout glyphLayout = new GlyphLayout();




    @Override
    public void init() {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Segoe UI.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 24;
        fontParameter.color = new Color(0x202124ff);
        font = fontGenerator.generateFont(fontParameter);
    }

    @Override
    public void tick(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        font.draw(batch, "No internet", 100, 100);
    }

    @Override
    public void dispose() {
        fontGenerator.dispose();
        font.dispose();
    }
}

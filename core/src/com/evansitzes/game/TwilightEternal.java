package com.evansitzes.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by evan on 6/8/16.
 */
public class TwilightEternal extends Game {
    public Configuration config;
    public SpriteBatch batch;
    public BitmapFont font;

    public TwilightEternal(final Configuration config) {
        this.config = config;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
//        this.setScreen(new GameScreen(this));
        this.setScreen(new TitleScreen(this));
    }

    @Override
    public void render () {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}

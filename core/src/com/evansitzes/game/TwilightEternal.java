package com.evansitzes.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evansitzes.game.entity.team.Player;
import com.evansitzes.game.screens.TitleScreen;

/**
 * Created by evan on 6/8/16.
 */
public class TwilightEternal extends Game {
    public Configuration config;
    public SpriteBatch batch;
    public BitmapFont font;
    public Player player;

    public static final AssetManager assets = new AssetManager();

    public TwilightEternal(final Configuration config) {
        this.config = config;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        player = new Player(this);
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

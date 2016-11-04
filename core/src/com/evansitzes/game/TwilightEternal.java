package com.evansitzes.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evansitzes.game.entity.team.Player;
import com.evansitzes.game.helpers.MyDataYamlFile;
import com.evansitzes.game.model.CharactersEnvelope;

/**
 * Created by evan on 6/8/16.
 */
public class TwilightEternal extends Game {
    public Configuration config;
    public SpriteBatch batch;
    public BitmapFont font;
    public Player player;
    private GameflowController gameflowController;
    private CharactersEnvelope players;

    public static final AssetManager assets = new AssetManager();

    public TwilightEternal(final Configuration config) {
        this.config = config;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        players = new MyDataYamlFile().loadData();
        player = new Player(this, players.getCharacters().get(0));
        //Use LibGDX's default Arial font.
        font = new BitmapFont();

        gameflowController = new GameflowController(this);
        gameflowController.setTitleScreen();
//        this.setScreen(new TitleScreen(this));
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

package com.evansitzes.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.evansitzes.game.entity.team.Player;
import com.evansitzes.game.helpers.PlayerStatsHelper;
import com.evansitzes.game.helpers.YamlParser;

/**
 * Created by evan on 6/8/16.
 */
public class TwilightEternal extends Game {
    public Configuration config;
    public SpriteBatch batch;
    public BitmapFont font;
    public Player player;
    public boolean debug;
    private GameflowController gameflowController;

    public ShapeRenderer shapeRenderer;
    public static final AssetManager assets = new AssetManager();

    public TwilightEternal(final Configuration config) {
        this.config = config;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        player = new Player(this, new YamlParser().loadData().getCharacters().get(0));
        shapeRenderer = new ShapeRenderer();
        debug = Configuration.DEBUG;

        new PlayerStatsHelper().buildPlayerStats(player);
        //Use LibGDX's default Arial font.
        font = new BitmapFont();

        gameflowController = new GameflowController(this);
//        gameflowController.setBattleScreen();
        gameflowController.setGameScreen();
//        gameflowController.setInventoryScreen();
//        gameflowController.setTitleScreen();
//        gameflowController.setShopScreen();
    }

    @Override
    public void render () {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public void savePlayerState() {
        YamlParser.savePlayer(player);
    }
}

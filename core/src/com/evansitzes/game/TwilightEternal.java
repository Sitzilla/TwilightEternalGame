package com.evansitzes.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evansitzes.game.entity.team.Player;
import com.evansitzes.game.helpers.PlayerStatsHelper;
import com.evansitzes.game.helpers.YamlParser;

import java.util.ArrayList;

/**
 * Created by evan on 6/8/16.
 */
public class TwilightEternal extends Game {
    public Configuration config;
    public SpriteBatch batch;
    public BitmapFont font;
    public Player player;
    private GameflowController gameflowController;

    public static final AssetManager assets = new AssetManager();

    public TwilightEternal(final Configuration config) {
        this.config = config;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        player = new Player(this, new YamlParser().loadData().getCharacters().get(0));
        new PlayerStatsHelper().buildPlayerStats(player);
        //Use LibGDX's default Arial font.
        font = new BitmapFont();

        gameflowController = new GameflowController(this);
                gameflowController.setBattleScreen();
//        gameflowController.setGameScreen();
//        gameflowController.setInventoryScreen();
//        gameflowController.setTitleScreen();
    }

    @Override
    public void render () {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public void savePlayerState(final ArrayList<String> equipment, final ArrayList<String> inventory) {
        new YamlParser().saveEquipment(equipment, inventory);
    }

    public void savePlayerGold(final int gold) {
        new YamlParser().saveGold(gold);
    }

}

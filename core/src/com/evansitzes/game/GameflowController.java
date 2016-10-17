package com.evansitzes.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.evansitzes.game.resources.Sounds;
import com.evansitzes.game.screens.BattleScreen;
import com.evansitzes.game.screens.GameScreen;
import com.evansitzes.game.screens.InventoryScreen;
import com.evansitzes.game.screens.TitleScreen;

/**
 * Created by evan on 10/14/16.
 */
public class GameflowController implements ApplicationListener {

    private TitleScreen titleScreen;
    private GameScreen gameScreen;
    private BattleScreen battleScreen;
    private InventoryScreen inventoryScreen;
    private final TwilightEternal game;


    public GameflowController(final TwilightEternal game) {
        this.game = game;
        gameScreen = new GameScreen(game, this);
    }

    @Override
    public void create() {


    }

    public void setTitleScreen() {
        titleScreen = new TitleScreen(game, this);
        game.setScreen(titleScreen);
    }

    public void setGameScreen() {
        Sounds.MAIN_THEME.play();
        game.setScreen(gameScreen);
    }

    public void setBattleScreen() {
        battleScreen = new BattleScreen(game, this);
        game.setScreen(battleScreen);
    }

    public void setInventoryScreen() {
        inventoryScreen = new InventoryScreen(game, this);
        game.setScreen(inventoryScreen);
    }



    @Override
    public void dispose() {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}


package com.evansitzes.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.evansitzes.game.Configuration;
import com.evansitzes.game.Level;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.conversation.BattleInterface;
import com.evansitzes.game.conversation.BattleStatus;
import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.loaders.BattleLevelLoader;
import com.evansitzes.game.resources.BattleChoiceEnum;

/**
 * Created by evan on 9/10/16.
 */
public class BattleScreen implements Screen, InputProcessor {

    private final TwilightEternal game;
    private final GameScreen gameScreen;
    private final OrthographicCamera camera;
    private float gameTime = 0;
    private final Configuration configuration;
    private Level level;
    private float delay;
    private boolean playersTurn;
    private boolean endBattle;

    private TiledMapRenderer tiledMapRenderer;

    private final Array<Enemy> enemies = new Array();

    private Skin skin;
    private Stage stage;
    private BattleInterface battleInterface;
    private BattleStatus battleStatus;
    private BattleChoiceEnum currentChoice;

    public BattleScreen(final TwilightEternal game, final GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        configuration = new Configuration();

        this.level = BattleLevelLoader.load(Vector2.Zero, game, this, "forest-battle-map");
        playersTurn = true;
        delay = 2;
        endBattle = false;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, level.mapWidth * level.tileWidth, level.mapHeight * level.tileHeight); // 1.5 of w and h
        camera.position.set(320, 240, 0);
        camera.update();

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("skins/golden-ui-skin.json"));
        battleStatus = new BattleStatus();
        battleInterface = new BattleInterface(enemies, game.player);
        stage.addActor(battleStatus);
        stage.addActor(battleInterface);

        Gdx.input.setInputProcessor(stage);
        final InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(level.map);
        tiledMapRenderer.setView(camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        for (final Enemy enemy : enemies) {
            enemy.draw();
        }
        game.player.draw();

        game.batch.end();

        gameTime += delta;

        stage.act(delta);
        stage.draw();

        currentChoice = battleInterface.pollChoice();

        if (playersTurn) {
            doPlayerAction();
        } else {
            doEnemyAction();
        }

    }

    private void doEnemyAction() {
        delay = 3;

        if (enemies.get(0).dead) {
            return;
        }
        
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                battleStatus.text("Enemy has attacked! \n You take " + enemies.get(0).hitDamage + " damage.\n ");
                game.player.takeDamage(enemies.get(0).hitDamage);
            }
        }, delay);

        playersTurn = true;
    }

    private void doPlayerAction() {
        switch (currentChoice) {
            case ATTACK:
                battleStatus.text("You have attacked! \n Enemy takes " + game.player.damage + " damage.\n ");
                // TODO allow selection of enemy
                delay = 2;
                enemies.get(0).takeDamage(game.player.damage);

                if (enemies.get(0).dead) {
                    battleStatus.text("You have killed the enemy!");
                }

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if (enemies.get(0).dead) {
                            endBattle();
                        }
                    }
                }, delay);

                battleInterface.resetChoice();
                playersTurn = false;
                break;
            case PEE_PANTS:
                battleStatus.text("Haha you have have peed your pants!\n");
                battleInterface.resetChoice();
                playersTurn = false;
                break;
            case RUN:
                battleStatus.text("You have run away!\n");
                battleInterface.resetChoice();
                delay = 2;

                Timer.schedule(new Timer.Task() {

                    @Override
                    public void run() {
                        endBattle();
                    }
                }, delay);
                playersTurn = false;
                break;
        }
    }

    private void endBattle() {


        game.setScreen(gameScreen);
    }

    @Override
    public void resize(final int width, final int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

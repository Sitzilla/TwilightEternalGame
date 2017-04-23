package com.evansitzes.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.evansitzes.game.Configuration;
import com.evansitzes.game.GameflowController;
import com.evansitzes.game.Level;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.conversation.BattleInterface;
import com.evansitzes.game.conversation.BattleStatus;
import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.helpers.BattleChoiceEnum;
import com.evansitzes.game.helpers.Sounds;
import com.evansitzes.game.helpers.Textures;
import com.evansitzes.game.loaders.BattleLevelLoader;

import java.util.*;

/**
 * Created by evan on 9/10/16.
 */
public class BattleScreen extends TwilightEternalScreen implements Screen {

    private static final int MAXIMUM_GOLD_PER_KILL = 5;

    private final TwilightEternal game;
    private final OrthographicCamera camera;
    private float gameTime;
    private final Configuration configuration;
    private Level level;
    private float delay;
    private boolean endBattle;
    private Table table;
    private Entity currentCombatant;
    private boolean isBattleCurrentlyDelayed = false;

    private NinePatch health;
    private NinePatch container;
    private float width;
    private int totalBarWidth;
    private TextureRegion gradient;
    private TextureRegion containerRegion;
    private BitmapFont font;

    private TiledMapRenderer tiledMapRenderer;

    private final Array<Enemy> enemies = new Array();
    private final Stack<Entity> orderedCombatants = new Stack<Entity>();

    private Skin skin;
    private BattleInterface battleInterface;
    private BattleStatus battleStatus;
    private BattleChoiceEnum currentChoice;
    private GameflowController gameflowController;

    public BattleScreen(final TwilightEternal game, final GameflowController gameflowController) {
        this.game = game;
        this.gameflowController = gameflowController;
        configuration = new Configuration();

        this.level = BattleLevelLoader.load(Vector2.Zero, game, this, gameflowController.getCurrentGameZone() + "-battle");
        buildOrderedCombatants();
        currentCombatant = orderedCombatants.pop();
        delay = 2;
        endBattle = false;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, level.mapWidth * level.tileWidth, level.mapHeight * level.tileHeight); // 1.5 of w and h
        camera.position.set(320, 240, 0);
        camera.update();

        // TODO abstract out healthbar
        gradient = Textures.Life.LIFE_BAR;
        containerRegion = Textures.Life.LIFE_BAR_CONTAINER;
        health = new NinePatch(gradient, 0, 0, 0, 0);
        container = new NinePatch(containerRegion, 5, 5, 2, 2);
        totalBarWidth = 100;
        font = new BitmapFont();

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("skins/james/plain-james-ui.json"));

        battleStatus = new BattleStatus();
        battleInterface = new BattleInterface(enemies, game.player);
        stage.addActor(battleStatus);
        stage.addActor(battleInterface);

        Gdx.input.setInputProcessor(battleInterface.stage);
//        final InputMultiplexer multiplexer = new InputMultiplexer();
//        multiplexer.addProcessor(stage);
//        multiplexer.addProcessor(this);
//        Gdx.input.setInputProcessor(multiplexer);

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(level.map);
        tiledMapRenderer.setView(camera);
        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    @Override
    public void show() {
        Sounds.SWORD_UNSHEATHE.play();
//        MyInputProcessor inputProcessor = new MyInputProcessor();
//        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Battle Map Rendering Logic
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        game.batch.setProjectionMatrix(camera.combined);
        width = game.player.currentHealth / game.player.maxHealth * totalBarWidth;

        game.batch.begin();

        for (final Enemy enemy : enemies) {
            enemy.draw();
        }
        game.player.draw();

        font.draw(game.batch, "Current life: " + game.player.currentHealth + "/" + game.player.maxHealth, 490, 145);
        container.draw(game.batch, 505, 110, totalBarWidth + 10, 20);
        health.draw(game.batch, 510, 115, width, 10);

        game.batch.end();

        gameTime += delta;

        stage.act(delta);
        stage.draw();

        if (isBattleCurrentlyDelayed) {
            return;
        }

                // Battle Action Logic
        currentChoice = battleInterface.pollChoice();

        if (currentCombatant instanceof Enemy) {
            battleInterface.disableInterface();
            doEnemyAction();
        } else {
            battleInterface.enableInterface();
            doPlayerAction();
        }
    }

    private void doEnemyAction() {
        delay = 1;

        if (enemies.get(0).dead) {
            return;
        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                updateBattleStatus("Enemy has attacked! \n You take " + currentCombatant.damage + " damage.\n ");
                Sounds.MONSTER.play();
                game.player.takeDamage(currentCombatant.damage);
                updateNextCombatant();

                if(game.player.dead) {
                    endBattle();
                }
                isBattleCurrentlyDelayed = false;
            }
        }, delay);
        isBattleCurrentlyDelayed = true;
    }

    private void doPlayerAction() {
        switch (currentChoice) {
            case ATTACK:
                updateNextCombatant();
                updateBattleStatus("You have attacked! \n Enemy takes " + game.player.damage + " damage.\n ");
                // TODO allow selection of enemy
                delay = 2;
                Sounds.SWORD_SWING.play();
//                enemies.get(0).takesHit();
                enemies.get(0).takeDamage(game.player.damage);

                if (enemies.get(0).dead) {
                    final int goldWon = getRandomGold();
                    updateBattleStatus("You have killed the enemy! \n You have found " + goldWon + " gold!");
                    game.player.saveGold(goldWon);
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
                break;

            case PEE_PANTS:
                updateNextCombatant();
                updateBattleStatus("Haha you have have peed your pants!\n");
                battleInterface.resetChoice();
                updateNextCombatant();
                break;

            case RUN:
                updateBattleStatus("You have run away!\n");
                battleInterface.resetChoice();
                delay = 2;

                Timer.schedule(new Timer.Task() {

                    @Override
                    public void run() {
                        endBattle();
                    }
                }, delay);
                break;
        }
    }

    private void updateBattleStatus(final String text) {
        battleStatus.remove();
        battleInterface.remove();
        battleStatus = new BattleStatus();
        battleStatus.text(text);
        stage.addActor(battleStatus);
        stage.addActor(battleInterface);
    }

    private int getRandomGold() {
        final Random rand = new Random();
        return rand.nextInt(MAXIMUM_GOLD_PER_KILL) + 1;
    }

    private void endBattle() {
        gameflowController.setGameScreen();
    }

    private void buildOrderedCombatants() {
        for (final Entity enemy : enemies) {
            orderedCombatants.add(enemy);
        }
        orderedCombatants.add(game.player);

        Collections.sort(orderedCombatants, new Comparator<Entity>() {
            public int compare(final Entity s1, final Entity s2) {
                return ((Integer)s1.speed).compareTo(s2.speed);
            }
        });
    }

    private void updateNextCombatant() {
        if (orderedCombatants.isEmpty()) {
            buildOrderedCombatants();
        }

        currentCombatant = orderedCombatants.pop();
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
}

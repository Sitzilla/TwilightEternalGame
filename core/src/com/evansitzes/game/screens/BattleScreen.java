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
import com.evansitzes.game.battle.BattleInterfaceData;
import com.evansitzes.game.battle.BattleInterfaceSelection;
import com.evansitzes.game.battle.BattleSelectionPath;
import com.evansitzes.game.conversation.BattleInterface;
import com.evansitzes.game.conversation.BattleStatus;
import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.helpers.BattleChoiceEnum;
import com.evansitzes.game.helpers.DamageCalculator;
import com.evansitzes.game.helpers.Textures;
import com.evansitzes.game.loaders.BattleLevelLoader;

import java.util.*;

import static com.evansitzes.game.helpers.BattleChoiceEnum.PEE_PANTS;
import static com.evansitzes.game.helpers.BattleChoiceEnum.RUN;

/**
 * Created by evan on 9/10/16.
 */
public class BattleScreen extends TwilightEternalScreen implements Screen {

    private static final int MAXIMUM_GOLD_PER_KILL = 5;

    private final TwilightEternal game;
    private final OrthographicCamera camera;
    private float gameTime;
    private final Configuration configuration;
    private final Level level;
    private float delay;
    private final Table table;
    private Entity currentCombatant;
    private boolean isBattleCurrentlyDelayed = false;
    private boolean isPlayerMidSelection;
    private boolean isEnemySelected;
    private boolean endBattle;

    private final NinePatch health;
    private final NinePatch container;
    private final NinePatch enemySelector;
    private float width;
    private final int totalBarWidth;
    private final TextureRegion gradient;
    private final TextureRegion containerRegion;
    private final BitmapFont font;

    private final TiledMapRenderer tiledMapRenderer;

    private final Array<Enemy> enemies = new Array();
    private final Array<Enemy> liveEnemies = new Array();;

    private final Stack<Entity> orderedCombatants = new Stack<Entity>();

    private final Map<String, BattleSelectionPath> battleSelectionPathMap = new HashMap<String, BattleSelectionPath>();

    private final Skin skin;
    private final BattleInterface battleInterface;
    private BattleStatus battleStatus;
    private BattleChoiceEnum currentChoice;
    private final GameflowController gameflowController;

    public BattleScreen(final TwilightEternal game, final GameflowController gameflowController) {
        this.game = game;
        this.gameflowController = gameflowController;
        configuration = new Configuration();

        this.level = BattleLevelLoader.load(Vector2.Zero, game, this, gameflowController.getCurrentGameZone() + "-battle");
        liveEnemies.addAll(enemies);
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

        // TODO make this an arrow
        enemySelector = new NinePatch(gradient, 0, 0, 0, 0);

        totalBarWidth = 100;
        font = new BitmapFont();

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("skins/james/plain-james-ui.json"));

//        battleStatus = new BattleStatus();
//        battleInterface = new BattleInterface(enemies, game.player);
////        battleInterfaceData = new BattleInterfaceData();
////        battleInterfaceData.battleSelectionsOptions.add(new BattleInterfaceSelection("Attack", BattleChoiceEnum.ATTACK));
////        battleInterfaceData.battleSelectionsOptions.add(new BattleInterfaceSelection("Run", BattleChoiceEnum.RUN));
////        battleInterfaceData.battleSelectionsOptions.add(new BattleInterfaceSelection("Pee Pants", BattleChoiceEnum.PEE_PANTS));
//        stage.addActor(battleStatus);
//        stage.addActor(battleInterface);
//
//        Gdx.input.setInputProcessor(battleInterface.stage);
//        final InputMultiplexer multiplexer = new InputMultiplexer();
//        multiplexer.addProcessor(stage);
//        multiplexer.addProcessor(this);
//        Gdx.input.setInputProcessor(multiplexer);

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(level.map);
        tiledMapRenderer.setView(camera);
        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Currently hardcoding battle flow logic into a Battle Selection Paths Hashmap. This will most likely change
        // as more complicated battle logic is added.
        final BattleInterfaceData battleInterfaceData = new BattleInterfaceData();
        battleInterfaceData.battleSelectionsOptions.add(new BattleInterfaceSelection("Attack", BattleChoiceEnum.ATTACK));
        battleInterfaceData.battleSelectionsOptions.add(new BattleInterfaceSelection("Run", RUN));
//        battleInterfaceData.battleSelectionsOptions.add(new BattleInterfaceSelection("Pee Pants", BattleChoiceEnum.PEE_PANTS));
        battleSelectionPathMap.put("base", new BattleSelectionPath(battleInterfaceData, "", false, false));

        battleSelectionPathMap.put("attack", new BattleSelectionPath(new BattleInterfaceData(), "base", false, true));
        battleSelectionPathMap.put("run", new BattleSelectionPath(new BattleInterfaceData(), "base", false, false));
//        battleSelectionPathMap.put("pee_pants", new BattleSelectionPath(new BattleInterfaceData(), "base", false, false));

        battleStatus = new BattleStatus();
        battleInterface = new BattleInterface(enemies, game.player);
        battleInterface.setInterface(battleSelectionPathMap.get("base").battleInterfaceData);
        stage.addActor(battleStatus);
        stage.addActor(battleInterface);
        Gdx.input.setInputProcessor(battleInterface.stage);

    }

    @Override
    public void show() {
//        Sounds.SWORD_UNSHEATHE.play();
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

        if (isEnemySelected) {
            final Enemy enemy = liveEnemies.get(battleInterface.getCurrentChoiceIndex());
            enemySelector.draw(game.batch, enemy.getX() + 60, enemy.getY() + 75, 10, 5);
        }

        game.batch.end();

        gameTime += delta;

        stage.act(delta);
        stage.draw();

        if (isBattleCurrentlyDelayed) {
            return;
        }

        if (!endBattle) {
            // Battle Action Logic
            if (currentCombatant instanceof Enemy) {
                isPlayerMidSelection = false;
                battleInterface.disableInterface();
                doEnemyAction();
            } else {
                battleInterface.enableInterface();
                doPlayerAction();
            }
        }
    }

    private void doEnemyAction() {
        delay = 1;

//        if (enemies.get(0).dead) {
//            return;
//        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                final int damageTaken = DamageCalculator.calculatePhysicalDamage(currentCombatant, game.player);
                updateBattleStatus("Enemy has attacked! \n You take " + damageTaken + " damage.\n ");
//                Sounds.MONSTER.play();
                game.player.takeDamage(damageTaken);
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

        if (!isPlayerMidSelection) {
            battleInterface.setInterface(battleSelectionPathMap.get("base").battleInterfaceData);
        }

        isPlayerMidSelection = true;

        if (!battleInterface.isChoiceSelected()) {
            return;
        }

        isEnemySelected = false;

        // TODO move away from enums
        final BattleSelectionPath nextPath = battleSelectionPathMap.get(battleInterface.pollChoice().toString().toLowerCase());

        if (nextPath.isEnd) {
            final int enemyIndex = battleInterface.getCurrentChoiceIndex();
            updateNextCombatant();
            final int damageDealt = DamageCalculator.calculatePhysicalDamage(game.player, liveEnemies.get(enemyIndex));
            updateBattleStatus("You have attacked! \n Enemy takes " + damageDealt + " damage.\n ");
            delay = 2;
//                Sounds.SWORD_SWING.play();
//                enemies.get(0).takesHit();
            liveEnemies.get(enemyIndex).takeDamage(damageDealt);

            if (enemiesAreDead()) {
                final int goldWon = getRandomGold();
                updateBattleStatus("You have killed the enemy! \n You have found " + goldWon + " gold!");
                game.player.saveGold(goldWon);
                endBattle = true;
                battleInterface.disableInterface();
            }

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (enemiesAreDead()) {
                        endBattle();
                    }
                }
            }, delay);

            battleInterface.resetChoice();
            return;
        }

        if (nextPath.isOffensive) {
            updateLiveEnemies();

            // Select enemy
            final BattleInterfaceData battleInterfaceData = new BattleInterfaceData();
            for (int i = 0; i < enemies.size; i++) {
                if (enemies.get(i).dead) {
                    continue;
                }

                battleInterfaceData.battleSelectionsOptions.add(new BattleInterfaceSelection(enemies.get(i).name + String.valueOf(i), BattleChoiceEnum.END));
            }

            battleSelectionPathMap.put("end", new BattleSelectionPath(battleInterfaceData, "attack", true, false));
            battleInterface.setInterface(battleInterfaceData);
            isEnemySelected = true;
            return;
        }

        // TODO hardcoded selection now. Will update as battle logic is fleshed out
        if (battleInterface.pollChoice() == RUN) {
            updateBattleStatus("You have run away!\n");
            endBattle = true;
            battleInterface.resetChoice();
            delay = 2;

            Timer.schedule(new Timer.Task() {

                @Override
                public void run() {
                    endBattle();
                }
            }, delay);

            return;
        }

        if (battleInterface.pollChoice() == PEE_PANTS) {
            updateNextCombatant();
            updateBattleStatus("Haha you have have peed your pants!\n");
            endBattle = true;
            battleInterface.resetChoice();
            updateNextCombatant();
            return;
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
            if (!enemy.dead) {
                orderedCombatants.add(enemy);
            }
        }
        orderedCombatants.add(game.player);

        Collections.sort(orderedCombatants, new Comparator<Entity>() {
            public int compare(final Entity s1, final Entity s2) {
                return ((Integer)s1.totalDexterity).compareTo(s2.totalDexterity);
            }
        });
    }

    private void updateNextCombatant() {
        if (orderedCombatants.isEmpty()) {
            buildOrderedCombatants();
        }

        currentCombatant = orderedCombatants.pop();

        while (currentCombatant.dead) {
            if (orderedCombatants.isEmpty()) {
                buildOrderedCombatants();
            }

            currentCombatant = orderedCombatants.pop();
        }
    }

    private void updateLiveEnemies() {
        final Iterator<Enemy> iterator = liveEnemies.iterator();

        while (iterator.hasNext()) {
            final Enemy enemy = iterator.next();

            if (enemy.dead) {
                iterator.remove();
            }
        }

    }

    private boolean enemiesAreDead() {
        for (final Enemy enemy : enemies) {
            if (!enemy.dead) {
                return false;
            }
        }

        return true;
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

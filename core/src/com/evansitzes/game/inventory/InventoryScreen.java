package com.evansitzes.game.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.sprites.InventorySprite;
import com.evansitzes.game.resources.Textures.Life;
import com.evansitzes.game.screens.GameScreen;

/**
 * Created by evan on 9/27/16.
 */
public class InventoryScreen implements Screen {

    private static final int SIZE_OF_INVENTORY = 25;
    private static final int SIZE_OF_EQUIPMENT = 5;

    private InventoryActor inventoryActor;
    private EquipmentActor equipmentActor;
    private GameScreen gameScreen;
    private TwilightEternal game;
    public static Stage stage;
    public InventorySprite inventorySprite;
    private final OrthographicCamera camera;
    private NinePatch health;
    private NinePatch container;
    private float width;
    private int totalBarWidth;
    private TextureRegion gradient;
    private TextureRegion containerRegion;

    public InventoryScreen(final TwilightEternal game, final GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.inventorySprite = new InventorySprite(game);

        gradient = Life.LIFE_BAR;
        containerRegion = Life.LIFE_BAR_CONTAINER;

        health = new NinePatch(gradient, 0, 0, 0, 0);
        container = new NinePatch(containerRegion, 5, 5, 2, 2);
        totalBarWidth = 100;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 600); // 1.5 of w and h
        camera.position.set(320, 240, 0);
        camera.update();
    }

    @Override
    public void show() {
        // create the stage and make it receive all input
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        final Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        final DragAndDrop dragAndDrop = new DragAndDrop();
        inventoryActor = new InventoryActor(new Inventory(SIZE_OF_INVENTORY), dragAndDrop, skin);
        equipmentActor = new EquipmentActor(new Inventory(SIZE_OF_EQUIPMENT), dragAndDrop, skin);
        stage.addActor(inventoryActor);
        stage.addActor(equipmentActor);
        inventoryActor.setVisible(true);
        equipmentActor.setVisible(true);
    }

    @Override
    public void render(final float delta) {
        // the screen will have a dark grey background colour
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        width = game.player.currentHealth / game.player.maxHealth * totalBarWidth;

        // exit the inventory when spacebar key is pressed
        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            game.setScreen(gameScreen);
        }

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        inventorySprite.draw();
        health.draw(game.batch, 400, 200, width, 10);


        //Offset it by the dynamic bar, let's say the gradient is 4 high.
        container.draw(game.batch, 395, 195, totalBarWidth + 10, 20);
        health.draw(game.batch, 400, 200, width, 10);

        game.batch.end();

        // handle all inputs and draw the whole UI
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(final int width, final int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
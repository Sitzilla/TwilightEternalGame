package com.evansitzes.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.evansitzes.game.GameflowController;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.sprites.InventorySprite;
import com.evansitzes.game.inventory.Inventory;
import com.evansitzes.game.inventory.InventoryActor;
import com.evansitzes.game.resources.Textures.Life;

/**
 * Created by evan on 9/27/16.
 */
public class ShopScreen implements Screen {

    private static final int SIZE_OF_INVENTORY = 25;

    private InventoryActor inventoryActor;
    private Inventory inventory;
    private GameflowController gameflowController;
    private TwilightEternal game;
    public static Stage stage;
    public InventorySprite inventorySprite;
    private final OrthographicCamera camera;
    private NinePatch divider;
//    private NinePatch container;
    private float width;
//    private int totalBarWidth;
    private TextureRegion gradient;
    private TextureRegion containerRegion;
    private BitmapFont font;

    public ShopScreen(final TwilightEternal game, final GameflowController gameflowController) {
        this.game = game;
        this.gameflowController = gameflowController;
        this.inventorySprite = new InventorySprite(game);

        gradient = Life.LIFE_BAR;
        containerRegion = Life.LIFE_BAR_CONTAINER;

        divider = new NinePatch(gradient, 0, 0, 0, 0);
//        container = new NinePatch(containerRegion, 5, 5, 2, 2);
//        totalBarWidth = 100;

        inventory = new Inventory(SIZE_OF_INVENTORY);
        inventory.createRandomItems();

        font = new BitmapFont();

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
        inventoryActor = new InventoryActor(this, inventory, dragAndDrop, skin);
        stage.addActor(inventoryActor);
        inventoryActor.setVisible(true);
    }

    @Override
    public void render(final float delta) {
        // the screen will have a dark grey background colour
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        //TODO replace with better drawings
        width = game.player.currentHealth / game.player.maxHealth * 100;

        // exit the inventory when spacebar key is pressed
        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            gameflowController.setGameScreen();
        }

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
//        inventorySprite.draw();

//        container.draw(game.batch, 395, 195, totalBarWidth + 10, 20);
        divider.draw(game.batch, 400, 200, 10, width);

        font.draw(game.batch, "Press spacebar to exit", 300, 0);
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
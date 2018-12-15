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
import com.evansitzes.game.helpers.Sounds;
import com.evansitzes.game.helpers.Textures.Stats;
import com.evansitzes.game.helpers.YamlParser;
import com.evansitzes.game.inventory.*;

import java.util.HashMap;

/**
 * Created by evan on 9/27/16.
 */
public class ShopScreen extends TwilightEternalScreen implements Screen {

    private static final int SIZE_OF_SHOP_INVENTORY = 50;
    private static final int SIZE_OF_INVENTORY = 25;
    private static final int SIZE_OF_EQUIPMENT = 5;

    private HashMap<String, Integer> prices;
    private ShopInventoryActor shopInventoryActorActor;
    private CurrentInventory shopInventory;
    private InventoryActor inventoryActor;
    private CurrentInventory inventory;
    private EquipmentActor equipmentActor;
    private CurrentEquipment equipment;
    private GameflowController gameflowController;
    public InventorySprite inventorySprite;
    private final OrthographicCamera camera;
    private NinePatch divider;
    private float height;
    private TextureRegion gradient;
    private BitmapFont font;

    public ShopScreen(final String merchantName, final TwilightEternal game, final GameflowController gameflowController) {
        this.game = game;
        this.gameflowController = gameflowController;
        this.inventorySprite = new InventorySprite(game);

        prices = YamlParser.loadPrices();

        inventory = new CurrentInventory(SIZE_OF_INVENTORY);
        equipment = new CurrentEquipment(SIZE_OF_EQUIPMENT);
        inventory.populateInventory(game.player.inventory);
        equipment.populateEquipment(game.player.equipment);

        gradient = Stats.STATS_BAR_CONTAINER;

        divider = new NinePatch(gradient, 0, 0, 0, 0);

        shopInventory = new CurrentInventory(SIZE_OF_SHOP_INVENTORY);
        shopInventory.populateInventory(YamlParser.loadNpcConfiguration(merchantName).getCurrent_inventory());
        updateItemDescription(shopInventory);

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
        shopInventoryActorActor = new ShopInventoryActor(this, shopInventory, skin);
        shopInventoryActorActor.setPosition(80, 50);
        shopInventoryActorActor.setName("Shop CurrentInventory");
        inventoryActor = new InventoryActor(this, inventory, dragAndDrop, skin);
        inventoryActor.setPosition(700, 150);
        equipmentActor = new EquipmentActor(this, equipment, dragAndDrop, skin);
        equipmentActor.setPosition(1150, 400);

        System.out.println("Width: " + Gdx.graphics.getWidth());
        System.out.println("Height: " + Gdx.graphics.getHeight());

        stage.addActor(shopInventoryActorActor);
        stage.addActor(inventoryActor);
        stage.addActor(equipmentActor);
        shopInventoryActorActor.setVisible(true);
        inventoryActor.setVisible(true);
        equipmentActor.setVisible(true);
    }

    @Override
    public void render(final float delta) {
        // the screen will have a dark grey background colour
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        //TODO replace with better drawings
        height = 500;

        // exit the inventory when spacebar key is pressed
        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            game.player.inventory = inventory.getItems();
//            game.player.equi = inventory.getItems();
            gameflowController.setGameScreen();
        }

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
//        inventorySprite.draw();

//        container.draw(game.batch, 395, 195, totalBarWidth + 10, 20);
        divider.draw(game.batch, 200, 0, 10, height);
        font.draw(game.batch, "Current gold: " + game.player.gold, 300, 480);

        font.draw(game.batch, "Press spacebar to exit", 300, 0);
        game.batch.end();

        // handle all inputs and draw the whole UI
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void wasClicked(final SlotActor slotActor) {
        final Item item = slotActor.getSlot().getItem();

        if (item == null || prices.get(item.name) > game.player.gold) {
            return;
        }

        // Add to CurrentInventory
        Sounds.COINS.play();
        //TODO remove gold from item description
        inventory.store(item, 1);
        shopInventory.removeItem(item);
        game.player.gold -= prices.get(item.name);
//        game.player.saveEquipment(equipment.getItems(), inventory.getItems());
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

//    private static ArrayList<String> getCurrent_inventory(final String merchantName) {
//        final ArrayList<String> inventory = new ArrayList<String>();
//        YamlParser.loadNpcConfiguration(merchantName).getCurrent_inventory();
//        inventory.add("Apple");
//        inventory.add("Bone");
//        inventory.add("Veggies");
//
//        return inventory;
//    }

    private void updateItemDescription(final CurrentInventory inventory) {
        for (final Slot slot : inventory.getSlots()) {
            if (slot.isEmpty()) {
                continue;
            }

            final Item item = slot.getItem();
            item.description = item.description + " - " + prices.get(item.name) + " Gold";
        }

    }
}
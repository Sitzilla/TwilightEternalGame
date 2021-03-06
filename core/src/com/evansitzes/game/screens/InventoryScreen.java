package com.evansitzes.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.evansitzes.game.GameflowController;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.component.StatsBar;
import com.evansitzes.game.entity.sprites.InventorySprite;
import com.evansitzes.game.helpers.PlayerStatsHelper;
import com.evansitzes.game.helpers.Sounds;
import com.evansitzes.game.helpers.Textures.Stats;
import com.evansitzes.game.inventory.*;

import static com.evansitzes.game.inventory.InventoryTypeEnum.*;

/**
 * Created by evan on 9/27/16.
 */
public class InventoryScreen extends TwilightEternalScreen implements Screen {

    private static final int SIZE_OF_INVENTORY = 25;
    private static final int SIZE_OF_EQUIPMENT = 5;

    private InventoryActor inventoryActor;
    private final CurrentInventory inventory;
    private EquipmentActor equipmentActor;
    private final CurrentEquipment equipment;
    private final GameflowController gameflowController;
    public InventorySprite inventorySprite;
    private final OrthographicCamera camera;
    private final BitmapFont font;

    private final StatsBar lifeBar;
    private final StatsBar manaBar;

    public InventoryScreen(final TwilightEternal game, final GameflowController gameflowController) {
        this.game = game;
        this.gameflowController = gameflowController;
        this.inventorySprite = new InventorySprite(game);


        this.lifeBar = new StatsBar(Stats.LIFE_BAR, 100, "life");
        this.manaBar = new StatsBar(Stats.MANA_BAR, 100, "mana");

        inventory = new CurrentInventory(SIZE_OF_INVENTORY);
        equipment = new CurrentEquipment(SIZE_OF_EQUIPMENT);
        inventory.populateInventory(game.player.inventory);
        equipment.populateEquipment(game.player.equipment);

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

//        game.player.getArticles();
        inventoryActor = new InventoryActor(this, inventory, dragAndDrop, skin);
        inventoryActor.setPosition(80, 50);
        equipmentActor = new EquipmentActor(this, equipment, dragAndDrop, skin);
        equipmentActor.setPosition(200, 700);

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

        lifeBar.setWidth(game.player.currentHitPoints, game.player.totalHitPoints);
        manaBar.setWidth(game.player.currentMagicPoints, game.player.totalMagicPoints);

        // exit the inventory when spacebar key is pressed
        if (Gdx.input.isKeyPressed(Keys.SPACE)
                || Gdx.input.isKeyPressed(Keys.BACKSPACE)
                || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            gameflowController.setGameScreen();
        }

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        inventorySprite.draw();

        // Draw Equipment Titles
        font.draw(game.batch, "Helmet:", 50, 510);
        font.draw(game.batch, "Armor:", 50, 460);
        font.draw(game.batch, "Weapon:", 50, 410);
        font.draw(game.batch, "Pants:", 50, 360);
        font.draw(game.batch, "Shoes:", 50, 310);

        // Draw stats
        lifeBar.draw(game, 400, 300);
        manaBar.draw(game, 400, 260);

        // Draw Stats
        font.draw(game.batch, "Strength:", 450, 220);
        font.draw(game.batch, "Speed:", 450, 195);
        font.draw(game.batch, "Intelligence:", 450, 170);
        font.draw(game.batch, "Armor:", 450, 145);
        font.draw(game.batch, "Resistance:", 450, 120);
        font.draw(game.batch, String.valueOf(game.player.totalStrength), 535, 220);
        font.draw(game.batch, String.valueOf(game.player.totalSpeed), 535, 195);
        font.draw(game.batch, String.valueOf(game.player.totalIntelligence  ), 535, 170);
        font.draw(game.batch, String.valueOf(game.player.totalArmor), 535, 145);
        font.draw(game.batch, String.valueOf(game.player.totalResistance), 535, 120);


        font.draw(game.batch, "Press spacebar to exit", 300, 0);
        font.draw(game.batch, "Current player: " + game.player.name, 300, 500);
        font.draw(game.batch, "Current gold: " + game.player.gold, 300, 480);

        game.batch.end();

        // handle all inputs and draw the whole UI
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void doubleClickItem(final SlotActor slotActor) {
        final Item clickedItem = slotActor.getSlot().getItem();
        if (clickedItem == null) {
            return;
        }

        if (clickedItem.consumable != null) {
            Sounds.BOTTLE.play();

            if (clickedItem.consumable.get("heal") != null) {
                game.player.restoreLife(clickedItem.consumable.get("heal"));
            }

            if (clickedItem.consumable.get("magic-restore") != null) {
                game.player.restoreMana(clickedItem.consumable.get("magic-restore"));
            }

            inventory.removeItem(clickedItem);
            return;
        }

        if (isEquipment(slotActor)) {

            if (slotActor.getSlot().isEquipment()) {
                removeEquipment(slotActor);
                return;
            }

            final Slot targetEquipmentSlot = equipment.getEquipmentSlotOfSpecificType(clickedItem.inventoryType);

            if (targetEquipmentSlot.getItem() == null) {
                PlayerStatsHelper.addPlayerEquipment(game.player, clickedItem);
                inventory.removeItem(clickedItem);
                equipment.addItem(clickedItem);
            } else {
                PlayerStatsHelper.switchPlayerEquipment(game.player, targetEquipmentSlot.getItem(), clickedItem);
                inventory.removeItem(clickedItem);
                inventory.store(targetEquipmentSlot.getItem(), 1);
                equipment.removeItem(targetEquipmentSlot.getItem());
                equipment.addItem(clickedItem);
            }

        }
    }

    @Override
    public void switchItems(final Slot payloadSlot, final Slot targetSlot) {

        if (payloadSlot.isEquipment() && targetSlot.getItem() == null) {
            PlayerStatsHelper.removePlayerEquipment(game.player, payloadSlot.getItem());
        }

        if (targetSlot.isEquipment() && targetSlot.getItem() == null) {
            PlayerStatsHelper.addPlayerEquipment(game.player, payloadSlot.getItem());
        }

        if ((targetSlot.isEquipment() || payloadSlot.isEquipment()) &&
                targetSlot.getItem() != null) {
            if (targetSlot.isEquipment()) {
                PlayerStatsHelper.switchPlayerEquipment(game.player, targetSlot.getItem(), payloadSlot.getItem());
            } else {
                PlayerStatsHelper.switchPlayerEquipment(game.player, payloadSlot.getItem(), targetSlot.getItem());
            }
        }
    }

    @Override
    public void resize(final int width, final int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        // Save inventory to file
        game.player.equipment = equipment.getEquipment();
        game.player.inventory = inventory.getItems();

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

    private void removeEquipment(SlotActor slotActor) {
        final Item item = slotActor.getSlot().getItem();
        PlayerStatsHelper.removePlayerEquipment(game.player, item);
        equipment.removeItem(item);
        inventory.store(item, 1);
        return;
    }

    // TODO consider abstract this out with an "isEquipment" method in the item
    private static boolean isEquipment(final SlotActor slotActor) {
        final InventoryTypeEnum inventoryType = slotActor.getSlot().getItem().inventoryType;

        return inventoryType == HELMET ||
                inventoryType == ARMOR ||
                inventoryType == WEAPON ||
                inventoryType == PANTS ||
                inventoryType == SHOES;
    }
}
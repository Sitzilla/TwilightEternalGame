package com.evansitzes.game.entity.team;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.helpers.Textures;
import com.evansitzes.game.model.Character;

import java.util.ArrayList;

/**
 * Created by evan on 9/23/16.
 */
public class Player extends TeamMember {

    public Sprite battleSprite;

    public Player(final TwilightEternal game, final Character character) {
        super(game);
        name = character.getName();
        equipment = character.getEquipment();
        inventory = character.getInventory();
        gold = character.getGold();
        maxHealth = 50;
        currentHealth = 50;
        score = 25;
        dead = false;
        damage = 25;
        battleSprite = new Sprite(Textures.People.BATTLE_PLAYER);
        battleSprite.setPosition(450, 140);
    }

    @Override
    public void takeDamage(final int rawDamage) {
        currentHealth -= rawDamage;

        if (currentHealth <= 0) {
            kill();
        }
    }

    @Override
    public void draw() {
        battleSprite.draw(game.batch);
    }

    @Override
    public void handle(final float delta) {

    }

    @Override
    public void kill() {
        currentHealth = 0;
        dead = true;
        battleSprite.setRegion(Textures.Enemies.EXPLOSION);
    }

    public void saveEquipment(final ArrayList<String> equipment, final ArrayList<String> inventory) {
        this.equipment = equipment;
        this.inventory = inventory;
        game.savePlayerState(equipment, inventory);
    }

    public void saveGold(final int gold) {
        this.gold += gold;
        game.savePlayerGold(this.gold);
    }
}

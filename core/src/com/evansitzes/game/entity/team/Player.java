package com.evansitzes.game.entity.team;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.helpers.Textures;
import com.evansitzes.game.model.Character;

import java.util.HashMap;

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
        score = 25;
        dead = false;
        battleSprite = new Sprite(Textures.People.BATTLE_PLAYER);
        battleSprite.setPosition(450, 140);

        baseStrength = character.getBaseAttributes().get("strength");
        baseSpeed = character.getBaseAttributes().get("speed");
        baseIntelligence = character.getBaseAttributes().get("intelligence");
        baseHitPoints = character.getBaseAttributes().get("hitPoints");
        baseMagicPoints = character.getBaseAttributes().get("magicPoints");
        baseArmor = character.getBaseAttributes().get("armor");
        baseResistance = character.getBaseAttributes().get("resistance");

        currentHitPoints = character.getCurrentHitPoints();
        currentMagicPoints = character.getCurrentMagicPoints();
        maxHitPoints = baseHitPoints + hitPointsModifier;
        maxMagicPoints = baseMagicPoints + magicPointsModifier;

        level = character.getLevel();
        experience = character.getExperience();
    }

    @Override
    public void takeDamage(final int rawDamage) {
        currentHitPoints -= rawDamage;

        if (currentHitPoints <= 0) {
            kill();
        }
    }

    public void restoreLife(final int life) {
        currentHitPoints += life;

        if (currentHitPoints > maxHitPoints) {
            currentHitPoints = maxHitPoints;
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
        currentHitPoints = 0;
        dead = true;
        battleSprite.setRegion(Textures.Enemies.EXPLOSION);
    }

//    public void saveEquipment(final ArrayList<String> equipment, final ArrayList<String> inventory) {
//        this.equipment = equipment;
//        this.inventory = inventory;
//        game.savePlayerState(equipment, inventory);
//    }

//    public void saveGold(final int gold) {
//        this.gold += gold;
//        game.savePlayerGold(this.gold);
//    }
//
//    public void loseGold(int gold) {
//        this.gold -= gold;
//        game.savePlayerGold(this.gold);
//    }

    public void updateTotalAttributes() {
        totalStrength = baseStrength + strengthModifier;
        totalSpeed = baseSpeed + speedModifier;
        totalIntelligence = baseIntelligence + intelligenceModifier;
        totalHitPoints = baseHitPoints+ hitPointsModifier;
        totalMagicPoints = baseMagicPoints+ magicPointsModifier;
        totalArmor = baseArmor+ armorModifier;
        totalResistance = baseResistance + resistanceModifier;
    }

    public HashMap<String, Integer> getBaseAttributes() {
        final HashMap<String, Integer> baseAttributes = new HashMap<String, Integer>();

        baseAttributes.put("strength", baseStrength);
        baseAttributes.put("speed", baseSpeed);
        baseAttributes.put("intelligence", baseIntelligence);
        baseAttributes.put("hitPoints", baseHitPoints);
        baseAttributes.put("magicPoints", baseMagicPoints);
        baseAttributes.put("armor", baseArmor);
        baseAttributes.put("resistance", baseResistance);

        return baseAttributes;
    }
}

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
        baseDexterity = character.getBaseAttributes().get("dexterity");
        baseConstitution = character.getBaseAttributes().get("constitution");
        baseWisdom = character.getBaseAttributes().get("wisdom");
        baseIntelligence = character.getBaseAttributes().get("intelligence");
        baseCharisma = character.getBaseAttributes().get("charisma");
        baseArmorClass = character.getBaseArmor();

        totalArmorClass = baseArmorClass + armorClassModifier;

        currentHealth = character.getCurrentHealth();
        baseHealth = character.getBaseHealth();
        maxHealth = baseHealth + baseConstitution;
    }

    @Override
    public void takeDamage(final int rawDamage) {
        currentHealth -= rawDamage;

        if (currentHealth <= 0) {
            kill();
        }
    }

    public void restoreLife(final int life) {
        currentHealth += life;

        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
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
        totalDexterity = baseDexterity + dexterityModifier;
        totalConstitution = baseConstitution + constitutionModifier;
        totalWisdom = baseWisdom + wisdomModifier;
        totalIntelligence = baseIntelligence + intelligenceModifier;
        totalCharisma = baseCharisma + charismaModifier;
    }

    public HashMap<String, Integer> getBaseAttributes() {
        final HashMap<String, Integer> baseAttributes = new HashMap<String, Integer>();


        baseAttributes.put("strength", baseStrength);
        baseAttributes.put("dexterity", baseDexterity);
        baseAttributes.put("constitution", baseConstitution);
        baseAttributes.put("wisdom", baseWisdom);
        baseAttributes.put("intelligence", baseIntelligence);
        baseAttributes.put("charisma", baseCharisma);

        return baseAttributes;
    }
}

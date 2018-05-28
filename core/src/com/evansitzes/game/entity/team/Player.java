package com.evansitzes.game.entity.team;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.evansitzes.game.Configuration;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.sprites.AnimatedSprite;
import com.evansitzes.game.entity.sprites.SimpleSprite;
import com.evansitzes.game.helpers.DirectionEnum;
import com.evansitzes.game.helpers.DrawUtils;
import com.evansitzes.game.helpers.StateEnum;
import com.evansitzes.game.helpers.Textures;
import com.evansitzes.game.model.Character;

import java.util.HashMap;
import java.util.Map;

import static com.evansitzes.game.helpers.DirectionEnum.*;
import static com.evansitzes.game.helpers.StateEnum.IDLE;
import static com.evansitzes.game.helpers.StateEnum.WALKING;

/**
 * Created by evan on 9/23/16.
 */
public class Player extends TeamMember {

    public static final int MOMENT_SPEED = 3;

    public SimpleSprite simpleSprite;
    public AnimatedSprite animatedSprite;
    public Sprite battleSprite;

    public StateEnum state;
    public DirectionEnum direction;


    public Player(final TwilightEternal game, final Character character) {
        super(game);
        name = character.getName();
        equipment = character.getEquipment();
        inventory = character.getInventory();
        gold = character.getGold();
        score = 25;
        dead = false;

        baseStrength = character.getBaseAttributes().get("strength");
        baseSpeed = character.getBaseAttributes().get("speed");
        baseIntelligence = character.getBaseAttributes().get("intelligence");
        baseHitPoints = character.getBaseAttributes().get("hitPoints");
        baseMagicPoints = character.getBaseAttributes().get("magicPoints");
        baseArmor = character.getBaseAttributes().get("armor");
        baseResistance = character.getBaseAttributes().get("resistance");

        currentHitPoints = character.getCurrentHitPoints();
        currentMagicPoints = character.getCurrentMagicPoints();
//        maxHitPoints = baseHitPoints + hitPointsModifier;
//        maxMagicPoints = baseMagicPoints + magicPointsModifier;

        level = character.getLevel();
        experience = character.getExperience();

        battleSprite = new Sprite(Textures.People.BATTLE_PLAYER);
        battleSprite.setPosition(450, 140);

        this.state = IDLE;
        this.direction = DOWN;
        simpleSprite = new SimpleSprite(game, Textures.People.PLAYER);
        animatedSprite = new AnimatedSprite(game);

        position.x = Configuration.STARTING_POSITION_X;
        position.y = Configuration.STARTING_POSITION_Y;
        simpleSprite.position.set(Configuration.STARTING_POSITION_X, Configuration.STARTING_POSITION_Y);
        animatedSprite.position.set(Configuration.STARTING_POSITION_X, Configuration.STARTING_POSITION_Y);
    }

    @Override
    public void handle(final float delta) {

        // handle motion
        handleInput(delta);
        if (game.debug) {
            DrawUtils.draw(game, hitBox, Color.GREEN);
        }

        if (state == IDLE) {
            simpleSprite.handle(delta);
        } else if (state == WALKING) {

            animatedSprite.setCurrentDirection(direction);

            if (direction == RIGHT) {
                position.x += MOMENT_SPEED;
            } else if (direction == LEFT) {
                position.x -= MOMENT_SPEED;
            } if (direction == UP) {
                position.y += MOMENT_SPEED;
            } else if (direction == DOWN) {
                position.y -= MOMENT_SPEED;
            }

            this.hitBox.set(position.x, position.y, 30, 30);
            animatedSprite.position.set(position.x, position.y);
            simpleSprite.position.set(position.x, position.y);

            animatedSprite.handle(delta);
            state = IDLE;
        }
    }

    public void setToLandingPage(final float x, final float y) {
        hitBox.set(x, y, 30, 30);
        position.set(x, y);
//        locate(x, y);
        animatedSprite.position.set(x, y);
        simpleSprite.position.set(x, y);
    }

    private void handleInput(final float delta) {

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

        if (currentHitPoints > totalHitPoints) {
            currentHitPoints = totalHitPoints;
        }
    }

    @Override
    public void draw() {
        battleSprite.draw(game.batch);
    }

    @Override
    public void kill() {
        currentHitPoints = 0;
        dead = true;
        battleSprite.setRegion(Textures.Enemies.EXPLOSION);
    }

    public void updateTotalStats() {
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

    public void addStats(final Map<String, Integer> stats) {
        baseStrength += stats.get("strength");
        baseSpeed += stats.get("speed");
        baseIntelligence += stats.get("intelligence");

        baseHitPoints += stats.get("hitPoints");
        baseMagicPoints += stats.get("magicPoints");

        updateTotalStats();
        restoreMaxPoints();
    }

    private void restoreMaxPoints() {
        currentHitPoints = totalHitPoints;
        currentMagicPoints = totalMagicPoints;
    }
}

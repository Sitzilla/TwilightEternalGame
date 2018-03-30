package com.evansitzes.game.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.sprites.PlayerSprite;
import com.evansitzes.game.helpers.Textures.Npcs;

/**
 * Created by evan on 6/8/16.
 */
public abstract class Entity extends Rectangle {

    public final TwilightEternal game;
    public String name;
//    public int damage;
//    public int speed;
    public int life = 1;
    public boolean dead;

    public int baseStrength = 1;
    public int baseDexterity = 1;
    public int baseConstitution = 1;
    public int baseWisdom = 1;
    public int baseIntelligence = 1;
    public int baseCharisma = 1;
    public int baseArmorClass = 1;

    public int strengthModifier;
    public int dexterityModifier;
    public int constitutionModifier;
    public int wisdomModifier;
    public int intelligenceModifier;
    public int charismaModifier;
    public int armorClassModifier;

    public int totalStrength = 1;
    public int totalDexterity = 1;
    public int totalConstitution = 1;
    public int totalWisdom = 1;
    public int totalIntelligence = 1;
    public int totalCharisma = 1;
    public int totalArmorClass = 1;

    public int experience;
    public int level = 1;

    public Sprite sprite;
    public String conversationText;
    public final Vector3 position = new Vector3();
    public Rectangle rectangle;

    // TODO for portal replace with map
    private String destination;
    private String landing;

    public Entity(final TwilightEternal game) {
        this.game = game;
        rectangle = new Rectangle();
    }

    public boolean overlaps(final Entity entity) {
        if (rectangle == null) { return false; }

        return rectangle.overlaps(entity.rectangle);
    }

    public boolean wouldOverlap(final Entity entity, final PlayerSprite.Facing direction) {
        if (rectangle == null) { return false; }

        return rectangle.overlaps(entity.rectangle);
    }

    public void locate(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public abstract void handle(final float delta);

    public void setConversationRectangle() {}

    public String getDestination() {
        return destination;
    }

    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public String getLanding() {
        return landing;
    }

    public void setLanding(final String landing) {
        this.landing = landing;
    }

    public void setPosition(final String position) {
    }

    //TODO abstract?
    public void setText(final String text) {
    }

    // TODO enums maybe?
    public void setSprite(final String spriteName) {
        if (spriteName.equals("blacksmith")) {
            sprite = new Sprite(Npcs.BLACKSMITH);
            return;
        }

            if (spriteName.equals("merchant")) {
            sprite = new Sprite(Npcs.MERCHANT);
            return;
        }

        if (spriteName.equals("guard")) {
            sprite = new Sprite(Npcs.GUARD);
            return;
        }

        if (spriteName.equals("maid")) {
            sprite = new Sprite(Npcs.MAID);
            return;
        }
    }

    public void setSpritesPositions() {}
}

package com.evansitzes.game.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.helpers.DirectionEnum;
import com.evansitzes.game.helpers.Textures.Npcs;

/**
 * Created by evan on 6/8/16.
 */
public abstract class Entity {

    public final TwilightEternal game;
    public String name;
    public boolean dead;

    public int baseStrength;
    public int baseSpeed;
    public int baseIntelligence;
    public int baseHitPoints;
    public int baseMagicPoints;
    public int baseArmor;
    public int baseResistance;

    public int strengthModifier;
    public int speedModifier;
    public int intelligenceModifier;
    public int hitPointsModifier;
    public int magicPointsModifier;
    public int armorModifier;
    public int resistanceModifier;

    public int totalStrength;
    public int totalSpeed;
    public int totalIntelligence;
    public int totalHitPoints;
    public int totalMagicPoints;
    public int totalArmor;
    public int totalResistance;

    public int experience;
    public int level;

    public Sprite sprite;
    public String conversationText;
    public final Vector2 position = new Vector2();
    public Rectangle hitBox;

    // TODO for portal replace with map
    private String destination;
    private String landing;

    public Entity(final TwilightEternal game) {
        this.game = game;
        hitBox = new Rectangle();
    }

    public boolean overlaps(final Entity entity) {
        if (hitBox == null) { return false; }

        return hitBox.overlaps(entity.hitBox);
    }

    public boolean wouldOverlap(final Entity entity, final DirectionEnum direction) {
        if (hitBox == null) { return false; }

        return hitBox.overlaps(entity.hitBox);
    }

//    public void locate(final float x, final float y) {
//        this.x = x;
//        this.y = y;
//    }

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

    public void setPositioning(final String positioning) {
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

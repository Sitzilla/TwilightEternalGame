package com.evansitzes.game.entity.npc;

import com.badlogic.gdx.math.Rectangle;
import com.evansitzes.game.Configuration;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 6/10/16.
 */
public abstract class Npc extends Entity {

//    public Movement movement;

    public int life = 1;
    public int score = 0;
    public boolean invincible;
    public long lastHitTime = -1;
    public int hitDamage = 5;
    public long lastFired;
    public boolean dead = false;

    private Rectangle conversationRectangle;

    public Npc(final TwilightEternal game) {
        super(game);
        conversationRectangle = new Rectangle();
    }

    @Override
    public void handle(float delta) {

    }

    public boolean overlapsConversationZone(final Entity entity) {
        if (conversationRectangle == null) { return false; }

        return conversationRectangle.overlaps(entity.rectangle);
    }

    public void draw() {
        sprite.draw(game.batch);
    }

    @Override
    public void setSpritesPositions() {
        final Configuration configuration = new Configuration();
        sprite.setPosition(this.x - configuration.NPC_STARTING_OFFSET_X, this.y - configuration.NPC_STARTING_OFFSET_Y);

        this.rectangle.set(this.x - configuration.NPC_RECTANGLE_OFFSET_X,
                this.y - configuration.NPC_RECTANGLE_OFFSET_Y,
                configuration.NPC_RECTANGLE_WIDTH,
                configuration.NPC_RECTANGLE_HEIGHT);

        conversationRectangle.set(this.x - configuration.NPC_CONVERSATION_RECTANGLE_OFFSET_X,
                this.y - configuration.NPC_CONVERSATION_RECTANGLE_OFFSET_Y,
                configuration.NPC_CONVERSATION_RECTANGLE_WIDTH,
                configuration.NPC_CONVERSATION_RECTANGLE_HEIGHT);
    }

}
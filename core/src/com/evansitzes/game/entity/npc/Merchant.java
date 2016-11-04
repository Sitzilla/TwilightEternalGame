package com.evansitzes.game.entity.npc;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.helpers.Textures;

/**
 * Created by evan on 10/20/16.
 */
public class Merchant extends Npc {
    public Sprite sprite;

    private Rectangle conversationRectangle;

    public Merchant(final TwilightEternal game) {
        super(game);
        life = 1;
        score = 25;
        dead = false;
        final Textures texture = new Textures();
        conversationRectangle = new Rectangle();
        conversationText = "Greetings! Would you like to see my wares?";

        sprite = new Sprite(texture.getMerchant());
    }

    @Override
    public void draw() {
        sprite.setPosition(this.x - 10, this.y - 10);
        this.rectangle.set(this.x - 5 , this.y - 5 , 10, 10);
        sprite.draw(game.batch);
        conversationRectangle.set(this.x - 25, this.y - 25, 50, 50);
    }

    @Override
    public boolean overlapsConversationZone(final Entity entity) {
        if (conversationRectangle == null) { return false; }

        return conversationRectangle.overlaps(entity.rectangle);
    }

    @Override
    public void handle(final float delta) {

    }

    @Override
    public void kill() {
    }
}

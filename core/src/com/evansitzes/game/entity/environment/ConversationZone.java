package com.evansitzes.game.entity.environment;

import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 11/19/16.
 */
public class ConversationZone extends Entity {
    private String text;

    public ConversationZone(final TwilightEternal game) {
        super(game);
        this.hitBox.set(this.position.x, this.position.y, 20, 20);
    }

    @Override
    public void handle(final float delta) {

    }

    public boolean overlapsConversationZone(final Entity entity) {
        if (hitBox == null) { return false; }

        return hitBox.overlaps(entity.hitBox);
    }

    public String getText() {
        return text;
    }

    @Override
    public void setText(final String text) {
        this.text = text;
    }
}

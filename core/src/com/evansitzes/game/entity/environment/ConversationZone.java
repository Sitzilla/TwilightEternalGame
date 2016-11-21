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
        this.rectangle.set(this.x, this.y, 20, 20);
    }

    @Override
    public void handle(final float delta) {

    }

    public boolean overlapsConversationZone(final Entity entity) {
        if (rectangle == null) { return false; }

        return rectangle.overlaps(entity.rectangle);
    }

    public String getText() {
        return text;
    }

    @Override
    public void setText(final String text) {
        this.text = text;
    }
}

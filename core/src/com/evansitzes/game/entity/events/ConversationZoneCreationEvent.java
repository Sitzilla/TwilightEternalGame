package com.evansitzes.game.entity.events;

import com.evansitzes.game.entity.environment.ConversationZone;
import com.evansitzes.game.screens.GameScreen;

/**
 * Created by evan on 11/19/16.
 */
public class ConversationZoneCreationEvent extends Event {
    public ConversationZone conversationZone;

    public ConversationZoneCreationEvent(final ConversationZone conversationZone, final GameScreen gameScreen) {
        super(gameScreen);
        this.conversationZone = conversationZone;
        happen();
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void happen() {
        gameScreen.getConversationZones().add(conversationZone);
    }
}

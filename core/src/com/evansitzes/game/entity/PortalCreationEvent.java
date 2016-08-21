package com.evansitzes.game.entity;

import com.evansitzes.game.Event;
import com.evansitzes.game.GameScreen;

/**
 * Created by evan on 6/10/16.
 */
public class PortalCreationEvent extends Event {
    public Portal portal;

    public PortalCreationEvent(Portal portal, GameScreen gameScreen) {
        super(gameScreen);
        this.portal = portal;
        happen();
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void happen() {
        gameScreen.portals.add(portal);
    }
}

package com.evansitzes.game.entity.events;

import com.evansitzes.game.entity.environment.Portal;
import com.evansitzes.game.screens.GameScreen;

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
        gameScreen.getPortals().add(portal);
    }
}

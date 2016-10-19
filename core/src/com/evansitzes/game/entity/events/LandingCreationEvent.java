package com.evansitzes.game.entity.events;

import com.evansitzes.game.entity.environment.Landing;
import com.evansitzes.game.screens.GameScreen;

/**
 * Created by evan on 6/10/16.
 */
public class LandingCreationEvent extends Event {
    public Landing landing;

    public LandingCreationEvent(Landing portal, GameScreen gameScreen) {
        super(gameScreen);
        this.landing = landing;
        happen();
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void happen() {
        gameScreen.getLandings().add(landing);
    }
}

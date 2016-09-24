package com.evansitzes.game.entity;

import com.evansitzes.game.Event;
import com.evansitzes.game.screens.GameScreen;

/**
 * Created by evan on 6/9/16.
 */
public class WallCreationEvent extends Event {
    public Wall wall;

    public WallCreationEvent(Wall wall, GameScreen gameScreen) {
        super(gameScreen);
        this.wall = wall;
        happen();
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void happen() {
        gameScreen.getObstructables().add(wall);
        gameScreen.getWalls().add(wall);
    }
}

package com.evansitzes.game;

/**
 * Created by evan on 6/9/16.
 */
public abstract class Event {

    public GameScreen gameScreen;

    public Event(final GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public abstract boolean isReady();

    public abstract void happen();


}

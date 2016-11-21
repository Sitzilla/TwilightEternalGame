package com.evansitzes.game.entity.environment;

import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 6/10/16.
 */
public class Portal extends Entity {

    private String destination;
    private String landing;

    public Portal(final TwilightEternal game) {
        super(game);
        this.rectangle.set(this.x, this.y, 20, 20);
    }

    @Override
    public void handle(final float delta) {

    }

    public String getDestination() {
        return destination;
    }

    @Override
    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public String getLanding() {
        return landing;
    }

    @Override
    public void setLanding(final String landing) {
        this.landing = landing;
    }
}
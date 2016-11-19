package com.evansitzes.game.entity.environment;

import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 6/10/16.
 */
public class Portal extends Entity {

    private final String destination;
    private final String landing;

    public Portal(final TwilightEternal game, final String destination, final String landing) {
        super(game);
        this.destination = destination;
        this.landing = landing;
        this.rectangle.set(this.x, this.y, 20, 20);
    }

    @Override
    public void handle(final float delta) {

    }

    public String getDestination() {
        return destination;
    }

    public String getLanding() {
        return landing;
    }
}
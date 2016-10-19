package com.evansitzes.game.entity.environment;

import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 10/18/16.
 */
public class Landing extends Entity {

    private final String destination;

    public Landing(final TwilightEternal game, final String destination) {
        super(game);
        this.destination = destination;
        this.rectangle.set(this.x, this.y, 20, 20);
    }

    @Override
    public void handle(float delta) {

    }

    public String getDestination() {
        return destination;
    }
}
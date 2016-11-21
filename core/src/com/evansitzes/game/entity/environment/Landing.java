package com.evansitzes.game.entity.environment;

import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 10/18/16.
 */
public class Landing extends Entity {

    private String position;

    public Landing(final TwilightEternal game) {
        super(game);
        this.rectangle.set(this.x, this.y, 20, 20);
    }

    @Override
    public void handle(final float delta) {

    }

    public String getPosition() {
        return position;
    }
    @Override

    public void setPosition(final String position) {
        this.position = position;
    }
}
package com.evansitzes.game.entity.environment;

import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 10/18/16.
 */
public class Landing extends Entity {

    private String positioning;

    public Landing(final TwilightEternal game) {
        super(game);
        this.hitBox.set(this.position.x, this.position.y, 20, 20);
    }

    @Override
    public void handle(final float delta) {

    }

    public String getPositioning() {
        return positioning;
    }
    @Override

    public void setPositioning(final String positioning) {
        this.positioning = positioning;
    }
}
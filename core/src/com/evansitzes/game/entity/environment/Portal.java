package com.evansitzes.game.entity.environment;

import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 6/10/16.
 */
public class Portal extends Entity {

    public Portal(final TwilightEternal game) {
        super(game);
        this.rectangle.set(this.x, this.y, 20, 20);
    }

    @Override
    public void handle(float delta) {

    }
}
package com.evansitzes.game.entity.environment;

import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 6/9/16.
 */
public class Wall extends Entity {

    public Wall(final TwilightEternal game) {
        super(game);
        this.hitBox.set(this.position.x, this.position.y, 20, 20);
    }

    @Override
    public void handle(float delta) {

    }
}

package com.evansitzes.game.entity.enemy;

import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 6/9/16.
 */

public abstract class Enemy extends Entity {

    public int score;
    public int level;
    public int difficultyClass;

    public Enemy(final TwilightEternal game) {
        super(game);
        name = "enemy";
    }

    public abstract void takeDamage(final int rawDamage);
    public abstract void draw();
    public abstract void kill();

}
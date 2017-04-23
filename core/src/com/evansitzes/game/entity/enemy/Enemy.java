package com.evansitzes.game.entity.enemy;

import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 6/9/16.
 */

public abstract class Enemy extends Entity {

//    public Movement movement;

    public boolean expire = false;

    public int score = 0;
    public boolean invincible;
    public long lastHitTime = -1;
    public long lastFired;

    public Enemy(final TwilightEternal game) {
        super(game);
        name = "enemy";
//        this.movement = movement;
    }
    public abstract void takesHit();
    public abstract void takeDamage(final int rawDamage);
    public abstract void draw();
    public abstract void kill();

}
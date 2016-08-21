package com.evansitzes.game.entity.npc;

import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 6/10/16.
 */
public abstract class Npc extends Entity {

//    public Movement movement;

    public boolean expire = false;

    public int life = 1;
    public int score = 0;
    public boolean invincible;
    public long lastHitTime = -1;
    public int hitDamage = 5;
    public long lastFired;
    public boolean dead = false;

    public Npc(final TwilightEternal game) {
        super(game);
//        this.movement = movement;
    }

    public abstract void draw();
    public abstract void kill();

}
package com.evansitzes.game.entity.team;

import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by evan on 9/23/16.
 */
public abstract class TeamMember extends Entity {

//    public String name;
    public float maxHitPoints;
    public float maxMagicPoints;
    public float currentHitPoints;
    public float currentMagicPoints;
//    public float baseHealth;
    public int score;
//    public int damage;
//    public int speed;
    public int gold;
//    public boolean dead;
    public Map<String, String> equipment;
    public ArrayList<String> inventory;

    public TeamMember(final TwilightEternal game) {
        super(game);
        name = "teammember";
    }

    public abstract void takeDamage(final int rawDamage);
//    public abstract void restoreLife(final int life);
    public abstract void draw();
    public abstract void kill();

}

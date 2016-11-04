package com.evansitzes.game.entity.team;

import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

import java.util.ArrayList;

/**
 * Created by evan on 9/23/16.
 */
public abstract class TeamMember extends Entity {

    public String name;
    public float maxHealth;
    public float currentHealth;
    public int score;
    public int damage;
    public boolean dead;
    public ArrayList<String> equipment;

    public TeamMember(final TwilightEternal game) {
        super(game);
        name = "teammember";
    }

    public abstract void takeDamage(final int rawDamage);
    public abstract void draw();
    public abstract void kill();

}

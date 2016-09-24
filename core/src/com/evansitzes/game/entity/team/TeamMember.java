package com.evansitzes.game.entity.team;

import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 9/23/16.
 */
public abstract class TeamMember extends Entity {


    public TeamMember(final TwilightEternal game) {
        super(game);
        name = "teammember";
    }

    public abstract void draw();

}

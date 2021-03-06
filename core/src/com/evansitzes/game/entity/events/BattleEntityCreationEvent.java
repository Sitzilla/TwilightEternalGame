package com.evansitzes.game.entity.events;

import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.screens.BattleScreen;
import com.evansitzes.game.entity.enemy.Enemy;

/**
 * Created by evan on 6/9/16.
 */
//TODO combine with CreationEvent
public class BattleEntityCreationEvent extends Event {

    public Entity entity;

    public BattleEntityCreationEvent(Entity entity, BattleScreen battleScreen) {
        super(battleScreen);
        this.entity = entity;
        happen();
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void happen() {

        // TODO update instanceof to name check
        if (entity instanceof Enemy) {
            battleScreen.getEnemies().add((Enemy) entity);
        }
    }
}

package com.evansitzes.game.entity;

import com.evansitzes.game.Event;
import com.evansitzes.game.screens.GameScreen;
import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.entity.npc.Npc;

/**
 * Created by evan on 6/9/16.
 */
public class EntityCreationEvent extends Event {

    public Entity entity;

    public EntityCreationEvent(Entity entity, GameScreen gameScreen) {
        super(gameScreen);
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
            gameScreen.getEnemies().add((Enemy) entity);
        } else if (entity instanceof Npc) {
            gameScreen.getObstructables().add(entity);
            gameScreen.getNpcs().add((Npc) entity);
        }
    }
}

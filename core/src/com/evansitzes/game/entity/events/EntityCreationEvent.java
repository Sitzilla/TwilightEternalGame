package com.evansitzes.game.entity.events;

import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.entity.environment.ConversationZone;
import com.evansitzes.game.entity.environment.Landing;
import com.evansitzes.game.entity.environment.Portal;
import com.evansitzes.game.entity.environment.Wall;
import com.evansitzes.game.entity.npc.Npc;
import com.evansitzes.game.screens.GameScreen;

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

        // TODO this looks like it was implemented by a 5 year old
        if (entity instanceof Enemy) {
            gameScreen.getEnemies().add((Enemy) entity);
        } else if (entity instanceof Npc) {
            gameScreen.getObstructables().add(entity);
            gameScreen.getNpcs().add((Npc) entity);
        } else if (entity instanceof Wall) {
            gameScreen.getObstructables().add(entity);
            gameScreen.getWalls().add((Wall) entity);
        } else if (entity instanceof Portal) {
            gameScreen.getPortals().add((Portal) entity);
        } else if (entity instanceof Portal) {
            gameScreen.getPortals().add((Portal) entity);
        } else if (entity instanceof Landing) {
            gameScreen.getLandings().add((Landing) entity);
        } else if (entity instanceof ConversationZone) {
            gameScreen.getConversationZones().add((ConversationZone) entity);
        }
    }
}

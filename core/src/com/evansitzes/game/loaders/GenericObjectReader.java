package com.evansitzes.game.loaders;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.entity.events.EntityCreationEvent;
import com.evansitzes.game.entity.events.Event;
import com.evansitzes.game.exceptions.ErrorCreatingEntityException;
import com.evansitzes.game.screens.GameScreen;

import java.util.HashMap;

/**
 * Created by evan on 11/19/16.
 */
public class GenericObjectReader {

    public static Entity readEntity(final RectangleMapObject object, final TwilightEternal game, final HashMap<String, String> parameters) {
        final String clazz = parameters.get("path") + "." + parameters.get("name");
        try {
            final Entity entity = (Entity) Class.forName(clazz).getConstructor(TwilightEternal.class).newInstance(game);

            // TODO should probably just loop through dynamically and set the keys
            if (parameters.containsKey("conversationText")) {
                entity.conversationText = parameters.get("conversationText");
            }

            if (parameters.containsKey("tag")) {
                entity.name = parameters.get("tag");
            }

            if (parameters.containsKey("sprite")) {
                entity.setSprite(parameters.get("sprite"));
            }

            if (parameters.containsKey("destination")) {
                entity.setDestination(parameters.get("destination"));
            }

            if (parameters.containsKey("landing")) {
                entity.setLanding(parameters.get("landing"));
            }

            if (parameters.containsKey("position")) {
                entity.setPosition(parameters.get("position"));
            }

            if (parameters.containsKey("text")) {
                entity.setText(parameters.get("text"));
            }

            readRectanglePosition(entity, object);
            return entity;
        }
        catch (final Exception e) {
            throw new ErrorCreatingEntityException("Error creating Entity: " + e.getMessage());
        }
    }

    public static Event readEntityEvent(final Entity entity, final GameScreen gameScreen) {
        return new EntityCreationEvent(entity, gameScreen);
    }

    private static void readRectanglePosition(final Entity entity, final RectangleMapObject object) {
        entity.x = (Float) object.getProperties().get("x");
        entity.y = (Float) object.getProperties().get("y");
        entity.rectangle.x = (Float) object.getProperties().get("x");
        entity.rectangle.y = (Float) object.getProperties().get("y");
        entity.rectangle.width = (Float) object.getProperties().get("width");
        entity.rectangle.height = (Float) object.getProperties().get("height");
    }


}

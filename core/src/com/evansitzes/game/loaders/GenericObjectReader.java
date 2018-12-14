package com.evansitzes.game.loaders;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.entity.events.EntityCreationEvent;
import com.evansitzes.game.entity.events.Event;
import com.evansitzes.game.exceptions.ErrorCreatingEntityException;
import com.evansitzes.game.helpers.ItemSpriteParser;
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

            if (parameters.containsKey("itemSprite")) {
                entity.sprite = new Sprite(ItemSpriteParser.parse(parameters.get("itemSprite")));
            }

            if (parameters.containsKey("destination")) {
                entity.setDestination(parameters.get("destination"));
            }

            if (parameters.containsKey("landing")) {
                entity.setLanding(parameters.get("landing"));
            }

            if (parameters.containsKey("positioning")) {
                entity.setPositioning(parameters.get("positioning"));
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
        entity.position.x = (Float) object.getProperties().get("x");
        entity.position.y = (Float) object.getProperties().get("y");
        entity.hitBox.x = (Float) object.getProperties().get("x");
        entity.hitBox.y = (Float) object.getProperties().get("y");
        entity.hitBox.width = (Float) object.getProperties().get("width");
        entity.hitBox.height = (Float) object.getProperties().get("height");
    }


}

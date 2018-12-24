package com.evansitzes.game.loaders;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.evansitzes.game.Level;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.entity.events.Event;
import com.evansitzes.game.helpers.YamlParser;
import com.evansitzes.game.model.NpcConfiguration;
import com.evansitzes.game.screens.GameScreen;

import java.util.HashMap;

/**
 * Created by evan on 6/9/16.
 */
public class TmxLevelLoader {

    private static final String ENVIRONMENT_ENTITIES_PATH = "com.evansitzes.game.entity.environment";
    private static final String ENEMY_ENTITY_PATH = "com.evansitzes.game.entity.enemy";
    private static final String NPC_ENTITY_PATH = "com.evansitzes.game.entity.npc";

    public static Level load(final Vector2 gridPosition, final TwilightEternal game, final GameScreen gameScreen, final String zone) {
        final TiledMap map = loadMap(zone);
        final Level level = new Level();

        setDefaults(level);
        level.name = (String) map.getProperties().get("name");
        level.level.set(gridPosition.x, gridPosition.y);

        final TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) map.getLayers().get("background1");

        // Get collision tiles
//        final TiledMapTileLayer collisionMapTileLayer = (TiledMapTileLayer) map.getLayers().get("collision");
//        final Array<RectangleMapObject> collisionObjects = new Array<RectangleMapObject>();
//
//        for (int i = 0; i < collisionMapTileLayer.getWidth(); i++) {
//            for (int j = 0; j < collisionMapTileLayer.getHeight(); j++) {
//                final Cell mycell = collisionMapTileLayer.getCell(i, j);
//
//                if (mycell == null) {
//                    continue;
//                }
//
//                collisionObjects.add(new RectangleMapObject(i * 26, j * 26, 26, 26));
//            }
//        }

        final MapLayer objectLayer = map.getLayers().get("objects");

        final MapObjects allObjects = objectLayer.getObjects();
        final Array<RectangleMapObject> objects = allObjects.getByType(RectangleMapObject.class);
//        final Array<MapObject> objects = allObjects.getByType(MapObject.class);

        level.map = map;
        level.mapHeight = map.getProperties().get("height", Integer.class);
        level.mapWidth = map.getProperties().get("width", Integer.class);
        level.tileHeight = map.getProperties().get("tileheight", Integer.class);
        level.tileWidth = map.getProperties().get("tilewidth", Integer.class);
        level.length =  tiledMapTileLayer.getHeight() * level.tileSize;
        level.velocity.set(0, 32);

        // TODO create wall tiles
        for (final MapLayer layer : map.getLayers()) {
            if (!layer.getName().contains("Wall")) {
                continue;
            }

            System.out.println(layer);
        }

        return mapObjectsToEntity(game, level, objects, gameScreen);
    }

    private static Level mapObjectsToEntity(final TwilightEternal game, final Level level, final Array<RectangleMapObject> objects, final GameScreen gameScreen) {
        for(final RectangleMapObject object : objects) {
            final HashMap<String, String> entityParameters = new HashMap<String, String>();
            final MapProperties properties = object.getProperties();
            final String type = (String) properties.get("type");
            if (type.equals("enemy")) {
                entityParameters.put("name", object.getName());
                entityParameters.put("path", ENEMY_ENTITY_PATH);

                createEntityAndAddToLevel(game, level, entityParameters, object, gameScreen);
            } else if (type.equals("wall")) {
                entityParameters.put("name", "Wall");
                entityParameters.put("path", ENVIRONMENT_ENTITIES_PATH);

                createEntityAndAddToLevel(game, level, entityParameters, object, gameScreen);
            } else if (type.equals("portal")) {
                entityParameters.put("name", "Portal");
                entityParameters.put("destination", (String) object.getProperties().get("destination"));
                entityParameters.put("landing", (String) object.getProperties().get("landing"));
                entityParameters.put("path", ENVIRONMENT_ENTITIES_PATH);

                createEntityAndAddToLevel(game, level, entityParameters, object, gameScreen);
            } else if (type.equals("npc")) {
                entityParameters.put("name", object.getName());
                entityParameters.put("path", NPC_ENTITY_PATH);

                final NpcConfiguration configuration = YamlParser.loadNpcConfiguration((String) object.getProperties().get("tag"));

                if (configuration.isWalking()) {
                    entityParameters.put("walking", (String) object.getProperties().get("walking-state"));
                    entityParameters.put("walking-width", (String) object.getProperties().get("walking-width"));
                }

                entityParameters.put("tag", configuration.getTag());
                entityParameters.put("sprite", configuration.getSprite());
                entityParameters.put("conversationText", configuration.getText());

                final Entity npc = GenericObjectReader.readEntity(object, game, entityParameters);
                npc.setSpritesPositions(); // TODO wtf should this be called from?
                final Event npcEvent = GenericObjectReader.readEntityEvent(npc, gameScreen);
                level.events.add(npcEvent);
            } else if (type.equals("landing")) {
                entityParameters.put("name", "Landing");
                entityParameters.put("positioning", (String) object.getProperties().get("positioning"));
                entityParameters.put("path", ENVIRONMENT_ENTITIES_PATH);

                createEntityAndAddToLevel(game, level, entityParameters, object, gameScreen);
            } else if (type.equals("conversation")) {
                entityParameters.put("name", "ConversationZone");
                entityParameters.put("text", (String) object.getProperties().get("text"));
                entityParameters.put("path", ENVIRONMENT_ENTITIES_PATH);

                createEntityAndAddToLevel(game, level, entityParameters, object, gameScreen);
            } else if (type.equals("environment-item")) {
                entityParameters.put("name", "EnvironmentItem");
                entityParameters.put("tag", object.getName());
                entityParameters.put("itemSprite", object.getName());
                entityParameters.put("path", ENVIRONMENT_ENTITIES_PATH);

                createEntityAndAddToLevel(game, level, entityParameters, object, gameScreen);
            }

        }

        return level;
    }

    private static Level createEntityAndAddToLevel(final TwilightEternal game,
                                                  final Level level,
                                                  final HashMap<String,
                                             String> entityParameters,
                                                  final RectangleMapObject object,
                                                  final GameScreen gameScreen) {
        final Entity conversation = GenericObjectReader.readEntity(object, game, entityParameters);
        final Event conversationEvent = GenericObjectReader.readEntityEvent(conversation, gameScreen);
        level.events.add(conversationEvent);

        return level;
    }

    private static void setDefaults(final Level level) {
        level.position.set(0, 0);
        level.velocity.set(0, 0);
    }

    private static TiledMap loadMap(final String zone) {
        final String resource = "environment/" + zone + ".tmx";
        final TmxMapLoader tmxMapLoader = new TmxMapLoader(new InternalFileHandleResolver());
        return tmxMapLoader.load(resource);
    }
}

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
import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.entity.environment.Landing;
import com.evansitzes.game.entity.environment.Portal;
import com.evansitzes.game.entity.environment.Wall;
import com.evansitzes.game.entity.events.*;
import com.evansitzes.game.entity.npc.Npc;
import com.evansitzes.game.screens.GameScreen;

/**
 * Created by evan on 6/9/16.
 */
public class TmxLevelLoader {

    private static final EnemyReader ENEMY_READER = new EnemyReader();
    private static final PositionReader POSITION_READER = new PositionReader();

    public static Level load(final Vector2 gridPosition, final TwilightEternal game, final GameScreen gameScreen, final String zone) {
        final TiledMap map = loadMap(zone);
        final Level level = new Level();

        setDefaults(level);
        level.name = (String) map.getProperties().get("name");
        level.level.set(gridPosition.x, gridPosition.y);

        final TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) map.getLayers().get("GroundBottomLayer");

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

        for(RectangleMapObject object : objects) {
            final MapProperties properties = object.getProperties();
            final String type = (String) properties.get("type");
            if (type.equals("enemy")) {
                final Enemy enemy = ENEMY_READER.readEnemy(object, game);
                    final Event enemyEvent = readEntityEvent(enemy, gameScreen);
                    level.events.add(enemyEvent);

            } else if (type.equals("wall")) {
                final Wall wall = readWall(object, game);
                final Event wallEvent = readWallEvent(wall, gameScreen);
                level.events.add(wallEvent);
            } else if (type.equals("portal")) {
                final Portal portal = readPortal(object, game);
                final Event portalEvent = readPortalEvent(portal, gameScreen);
                level.events.add(portalEvent);
            } else if (type.equals("npc")) {
                final Npc npc = readNpc(object, game);
                final Event portalEvent = readEntityEvent(npc, gameScreen);
                level.events.add(portalEvent);
            } else if (type.equals("landing")) {
                final Landing landing = readLanding(object, game);
                final Event landingEvent = readLandingEvent(landing, gameScreen);
                level.events.add(landingEvent);
            }
        }

        return level;
    }

    private static Portal readPortal(final RectangleMapObject object, final TwilightEternal game) {
        final String clazz = "com.evansitzes.game.entity.environment.Portal";
        try {
//            final Portal portal = (Portal) Class.forName(clazz).getConstructor(TwilightEternal.class).newInstance(game);
            final Portal portal = new Portal(game, (String) object.getProperties().get("destination"));
            readRectanglePosition(portal, object);
            return portal;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Event readPortalEvent(final Portal portal, final GameScreen gameScreen) {
        return new PortalCreationEvent(portal, gameScreen);
    }

    private static Landing readLanding(final RectangleMapObject object, final TwilightEternal game) {
        final String clazz = "com.evansitzes.game.entity.environment.Landing";
        try {
            final Landing landing = new Landing(game, (String) object.getProperties().get("destination"));
            readRectanglePosition(landing, object);
            return landing;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Event readLandingEvent(final Landing landing, final GameScreen gameScreen) {
        return new LandingCreationEvent(landing, gameScreen);
    }

    private static Wall readWall(final RectangleMapObject object, final TwilightEternal game) {
        final String clazz = "com.evansitzes.game.entity.environment.Wall";
        try {
            final Wall wall = (Wall) Class.forName(clazz).getConstructor(TwilightEternal.class).newInstance(game);
            readRectanglePosition(wall, object);
            return wall;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Event readWallEvent(final Wall wall, final GameScreen gameScreen) {
        return new WallCreationEvent(wall, gameScreen);
    }

    private static Npc readNpc(final RectangleMapObject object, final TwilightEternal game) {
        final String clazz = "com.evansitzes.game.entity.npc." + object.getName();
        try {
            final Npc npc = (Npc) Class.forName(clazz).getConstructor(TwilightEternal.class).newInstance(game);
            POSITION_READER.readEntityPosition(npc, object);
            npc.setSpritesPositions();
            return npc;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Event readEntityEvent(final Entity entity, final GameScreen gameScreen) {
        return new EntityCreationEvent(entity, gameScreen);
    }

    private static void readRectanglePosition(final Entity entity, final RectangleMapObject object) {
        entity.rectangle.x = (Float) object.getProperties().get("x");
        entity.rectangle.y = (Float) object.getProperties().get("y");
        entity.rectangle.width = (Float) object.getProperties().get("width");
        entity.rectangle.height = (Float) object.getProperties().get("height");
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

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
import com.evansitzes.game.entity.events.Event;
import com.evansitzes.game.Level;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.events.BattleEntityCreationEvent;
import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.screens.BattleScreen;

/**
 * Created by evan on 9/10/16.
 */
public class BattleLevelLoader {

    private static final EnemyReader ENEMY_READER = new EnemyReader();

    public static Level load(final Vector2 gridPosition, final TwilightEternal game, final BattleScreen battleScreen, final String zone) {
        final TiledMap map = loadMap(zone);
        final Level level = new Level();
        level.name = (String) map.getProperties().get("name");
        level.level.set(gridPosition.x, gridPosition.y);

        final TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) map.getLayers().get("tiles");

        level.map = map;
        level.mapHeight = map.getProperties().get("height", Integer.class);
        level.mapWidth = map.getProperties().get("width", Integer.class);
        level.tileHeight = map.getProperties().get("tileheight", Integer.class);
        level.tileWidth = map.getProperties().get("tilewidth", Integer.class);
        level.velocity.set(0, 32);


        final MapLayer objectLayer = map.getLayers().get("objects");

        final MapObjects allObjects = objectLayer.getObjects();
        final Array<RectangleMapObject> objects = allObjects.getByType(RectangleMapObject.class);

        for(RectangleMapObject object : objects) {
            final MapProperties properties = object.getProperties();
            final String type = (String) properties.get("type");
            if (type.equals("enemy")) {
                final Enemy enemy = ENEMY_READER.readEnemy(object, game);
                final Event enemyEvent = readEntityEvent(enemy, battleScreen, game, object);
                level.events.add(enemyEvent);

            }
            else if (type.equals("player")) {
                final Event playerEvent = readEntityEvent(game.player, battleScreen, game, object);
                level.events.add(playerEvent);
            }
        }

        return level;
    }

    private static Event readEntityEvent(final Entity entity, final BattleScreen battleScreen, final TwilightEternal game, final RectangleMapObject object) {
        return new BattleEntityCreationEvent(entity, battleScreen);
    }

    private static TiledMap loadMap(final String zone) {
        final String resource = "environment/" + zone + ".tmx";
        final TmxMapLoader tmxMapLoader = new TmxMapLoader(new InternalFileHandleResolver());
        return tmxMapLoader.load(resource);
    }

}

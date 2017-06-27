package com.evansitzes.game.loaders;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.enemy.Enemy;

/**
 * Created by evan on 9/10/16.
 */
public class EnemyReader {

    private static final PositionReader POSITION_READER = new PositionReader();

    public static Enemy readEnemy(final RectangleMapObject object, final TwilightEternal game) {
        final String clazz = "com.evansitzes.game.entity.enemy." + object.getName();
        try {
            final Enemy enemy = (Enemy) Class.forName(clazz).getConstructor(TwilightEternal.class).newInstance(game);
//            enemy.name = object.getName();
            POSITION_READER.readEntityPosition(enemy, object);
            return enemy;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
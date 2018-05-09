package com.evansitzes.game.loaders;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 9/10/16.
 */
public class PositionReader {

    // TODO put all readers in 1 class
    public static void readEntityPosition(final Entity entity, final RectangleMapObject object) {
        entity.position.x = (Float) object.getProperties().get("x");
        entity.position.y = (Float) object.getProperties().get("y");
        entity.hitBox.set(entity.position.x, entity.position.y, 25, 25);
//        entity.locate((Float) object.getProperties().get("x"), (Float) object.getProperties().get("y"));
    }


}
package com.evansitzes.game.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.evansitzes.game.TwilightEternal;

/**
 * Created by evan on 6/8/16.
 */
public abstract class Entity extends Rectangle {

    public final TwilightEternal game;
    public String name;

    public final Vector3 position = new Vector3();
    public Rectangle rectangle;

    public Entity(final TwilightEternal game) {
        this.game = game;
        rectangle = new Rectangle();
    }

    public boolean overlaps(final Entity entity) {
        if (rectangle == null) { return false; }

        return rectangle.overlaps(entity.rectangle);
    }

    public boolean wouldOverlap(final Entity entity, PlayerSprite.Facing direction) {
        if (rectangle == null) { return false; }

        return rectangle.overlaps(entity.rectangle);
    }

    public void locate(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public abstract void handle(final float delta);

    public void setConversationRectangle() {}

}

package com.evansitzes.game.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.evansitzes.game.TwilightEternal;

/**
 * Created by evan on 6/8/16.
 */
public class SimpleSprite extends Entity {

    public final Sprite sprite;

    public SimpleSprite(final TwilightEternal game, final TextureRegion texture) {
        super(game);
        sprite = new Sprite(texture);
        rectangle = new Rectangle();
        rectangle.width = sprite.getWidth();
        rectangle.height = sprite.getHeight();
        sprite.setPosition(370, 600);

    }

    @Override
    public void handle(float delta) {
        sprite.setPosition(position.x, position.y);
        sprite.draw(game.batch);
    }
}

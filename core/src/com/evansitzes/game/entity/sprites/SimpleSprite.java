package com.evansitzes.game.entity.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.evansitzes.game.Configuration;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 6/8/16.
 */
public class SimpleSprite extends Entity {

    public final Sprite sprite;

    public SimpleSprite(final TwilightEternal game, final TextureRegion texture) {
        super(game);
        sprite = new Sprite(texture);
        hitBox = new Rectangle();
        hitBox.width = sprite.getWidth();
        hitBox.height = sprite.getHeight();
        sprite.setPosition(Configuration.STARTING_POSITION_X, Configuration.STARTING_POSITION_Y);

    }

    @Override
    public void handle(float delta) {
        sprite.setPosition(position.x, position.y);
        sprite.draw(game.batch);
    }
}

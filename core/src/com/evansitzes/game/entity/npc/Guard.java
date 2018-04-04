package com.evansitzes.game.entity.npc;

import com.badlogic.gdx.math.Rectangle;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.helpers.Textures;

/**
 * Created by evan on 6/10/16.
 */
public class Guard extends Npc {
    private Rectangle conversationRectangle;

    public Guard(final TwilightEternal game) {
        super(game);
        score = 25;
        dead = false;
        final Textures texture = new Textures();

        conversationRectangle = new Rectangle();
//        conversationText = "No one is allowed to enter the castle at this moment.";
//        sprite = new Sprite(texture.getGuard());
        //        sprite.setSize((float) (sprite.getWidth() * 0.4), (float) (sprite.getHeight() * 0.4));
    }


}

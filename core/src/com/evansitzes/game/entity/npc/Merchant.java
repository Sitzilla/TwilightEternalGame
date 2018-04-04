package com.evansitzes.game.entity.npc;

import com.badlogic.gdx.math.Rectangle;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.helpers.Textures;

/**
 * Created by evan on 10/20/16.
 */
public class Merchant extends Npc {
    private Rectangle conversationRectangle;

    public Merchant(final TwilightEternal game) {
        super(game);
        score = 25;
        dead = false;
        final Textures texture = new Textures();
        conversationRectangle = new Rectangle();
//        conversationText = "Greetings! Would you like to see my wares?";

//        sprite = new Sprite(Textures.Npcs.MERCHANT);
//        sprite = new Sprite(texture.getMerchant());
    }

}

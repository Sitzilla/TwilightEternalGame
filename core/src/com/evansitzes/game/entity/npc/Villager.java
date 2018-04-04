package com.evansitzes.game.entity.npc;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.helpers.Textures;

/**
 * Created by evan on 6/10/16.
 */
public class Villager extends Npc {
    private Rectangle conversationRectangle;

    public Villager(TwilightEternal game) {
        super(game);
        score = 25;
        dead = false;
        Textures texture = new Textures();
        conversationRectangle = new Rectangle();

        sprite = new Sprite(texture.getRandomNpc());
//        sprite.setSize((float) (sprite.getWidth() * 0.4), (float) (sprite.getHeight() * 0.4));
    }

}

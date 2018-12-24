package com.evansitzes.game.entity.npc;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.helpers.Textures.Npcs;

public class WalkingDog extends WalkingNpc {
    private Rectangle conversationRectangle;

    public WalkingDog(TwilightEternal game) {
        super(game);

        score = 25;
        dead = false;
        conversationRectangle = new Rectangle();
        sprite = new Sprite(Npcs.DOG);
        textures = new WalkingNpctTextures(Npcs.WALKING_LEFT, Npcs.WALKING_RIGHT, Npcs.WALKING_UP, Npcs.WALKING_DOWN);
    }

    @Override
    public void draw(final float delta) {
        handleMovement(delta);
    }
}

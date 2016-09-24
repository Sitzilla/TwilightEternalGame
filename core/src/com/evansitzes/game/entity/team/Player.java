package com.evansitzes.game.entity.team;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.resources.Textures;

/**
 * Created by evan on 9/23/16.
 */
public class Player extends TeamMember {

    public Sprite battleSprite;

    public Player(TwilightEternal game) {
        super(game);
        battleSprite = new Sprite(Textures.People.BATTLE_PLAYER);
        battleSprite.setPosition(450, 140);
    }

    @Override
    public void draw() {
        battleSprite.draw(game.batch);
    }

    @Override
    public void handle(float delta) {

    }
}

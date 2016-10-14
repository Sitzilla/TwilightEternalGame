package com.evansitzes.game.entity.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.team.TeamMember;
import com.evansitzes.game.resources.Textures;

/**
 * Created by evan on 9/27/16.
 */
public class InventorySprite extends TeamMember {

    public final Sprite sprite;

    public InventorySprite(final TwilightEternal game) {
        super(game);
        final TextureRegion texture = Textures.People.INVENTORY_PLAYER;
        sprite = new Sprite(texture);
        sprite.setPosition(400, 300);

    }

    @Override
    public void takeDamage(int rawDamage) {
        
    }

    @Override
    public void handle(float delta) {
    }

    @Override
    public void draw() {
        sprite.draw(game.batch);
    }

    @Override
    public void kill() {

    }
}

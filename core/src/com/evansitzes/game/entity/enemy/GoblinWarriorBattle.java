package com.evansitzes.game.entity.enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.resources.Textures;

/**
 * Created by evan on 9/10/16.
 */
public class GoblinWarriorBattle extends Enemy {
    public Sprite sprite;

    public GoblinWarriorBattle(final TwilightEternal game) {
        super(game);
        life = 30;
        score = 25;
        damage = 10;
        dead = false;
        sprite = new Sprite(Textures.Enemies.GOBLIN_WARRIOR_BATTLE);
        sprite.setSize((float) (sprite.getWidth() * 0.4), (float) (sprite.getHeight() * 0.4));
    }

    @Override
    public void takeDamage(final int rawDamage) {
        life -= rawDamage;

        if (life < 0) {
            kill();
        }
    }

    @Override
    public void draw() {
//        if (!dead) {
        sprite.setPosition(this.x, this.y);
        this.rectangle.set(this.x + 20, this.y + 20, 10, 10);
        sprite.draw(game.batch);
//        }
    }

    @Override
    public void handle(final float delta) {

    }

    @Override
    public void kill() {
        life = 0;
        dead = true;
        sprite.setRegion(Textures.Enemies.EXPLOSION);
    }
}

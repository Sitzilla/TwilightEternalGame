package com.evansitzes.game.entity.enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.helpers.Textures;

/**
 * Created by evan on 6/9/16.
 */
public class GoblinWarrior extends Enemy {
    public Sprite sprite;

    public GoblinWarrior(TwilightEternal game) {
        super(game);
        life = 1;
        score = 25;
        dead = false;
        sprite = new Sprite(Textures.Enemies.GOBLIN_WARRIOR);
        sprite.setSize((float) (sprite.getWidth() * 0.4), (float) (sprite.getHeight() * 0.4));
    }

    @Override
    public void takeDamage(final int rawDamage) {

    }

    @Override
    public void takesHit() {

    }

    @Override
    public void draw() {
//        if (!dead) {
        // TODO should I be setting position off the sprites position or the rectangles?
            sprite.setPosition(this.x, this.y);
            this.rectangle.set(this.x + 20, this.y + 20, 10, 10);
//            sprite.setPosition(this.rectangle.x, this.rectangle.y);
//            this.rectangle.set(this.rectangle.x + 20, this.rectangle.y + 20, 10, 10);
            sprite.draw(game.batch);
//        }
    }

    @Override
    public void handle(float delta) {

    }

    @Override
    public void kill() {
        life = 0;
        dead = true;
    }
}

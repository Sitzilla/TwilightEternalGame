package com.evansitzes.game.entity.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.helpers.DrawUtils;
import com.evansitzes.game.helpers.Textures;

/**
 * Created by evan on 6/9/16.
 */
public class GoblinWarrior extends Enemy {
    public Sprite sprite;

    public GoblinWarrior(TwilightEternal game) {
        super(game);
        baseHitPoints = 1;
        score = 25;
        level = 1;
        difficultyClass = 1;
        dead = false;
        sprite = new Sprite(Textures.Enemies.GOBLIN_WARRIOR);
        sprite.setSize((float) (sprite.getWidth() * 0.4), (float) (sprite.getHeight() * 0.4));
    }

    @Override
    public void takeDamage(final int rawDamage) {

    }

    @Override
    public void draw() {
        sprite.setPosition(this.position.x, this.position.y);
        this.hitBox.set(this.position.x + 20, this.position.y + 20, 10, 10);

        sprite.draw(game.batch);

        if (game.debug) {
            DrawUtils.draw(game, hitBox, Color.RED);
        }
    }

    @Override
    public void handle(float delta) {

    }

    @Override
    public void kill() {
        baseHitPoints = 0;
        dead = true;
    }
}

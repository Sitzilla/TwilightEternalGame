package com.evansitzes.game.entity.npc;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.resources.Textures;

/**
 * Created by evan on 6/10/16.
 */
public class Villager extends Npc {
    public Sprite sprite;

    public Villager(TwilightEternal game) {
        super(game);
        life = 1;
        score = 25;
        dead = false;
        Textures texture = new Textures();

        sprite = new Sprite(texture.getRandomNpc());
//        sprite.setSize((float) (sprite.getWidth() * 0.4), (float) (sprite.getHeight() * 0.4));
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
    public void handle(float delta) {

    }

    @Override
    public void kill() {
//        life = 0;
//        dead = true;
//        sprite.setRegion(Textures.Enemies.EXPLOSION);
    }
}

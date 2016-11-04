package com.evansitzes.game.entity.npc;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.helpers.Textures;

/**
 * Created by evan on 6/10/16.
 */
public class Guard extends Npc {
    public Sprite sprite;

    private Rectangle conversationRectangle;

    public Guard(final TwilightEternal game) {
        super(game);
        life = 1;
        score = 25;
        dead = false;
        final Textures texture = new Textures();

        conversationRectangle = new Rectangle();
        conversationText = "No one is allowed to enter the castle at this moment.";
        sprite = new Sprite(texture.getGuard());//        sprite.setSize((float) (sprite.getWidth() * 0.4), (float) (sprite.getHeight() * 0.4));
    }

    @Override
    public void draw() {
        sprite.setPosition(this.x, this.y);
        this.rectangle.set(this.x + 10, this.y + 10, 10, 10);
        sprite.draw(game.batch);
        conversationRectangle.set(this.x - 25, this.y - 25, 50, 50);
    }

    @Override
    public boolean overlapsConversationZone(final Entity entity) {
        if (conversationRectangle == null) { return false; }

        return conversationRectangle.overlaps(entity.rectangle);
    }

    @Override
    public void handle(final float delta) {

    }

    @Override
    public void kill() {
//        life = 0;
//        dead = true;
//        sprite.setRegion(Textures.Enemies.EXPLOSION);
    }
}

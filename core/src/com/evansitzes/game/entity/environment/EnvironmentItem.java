package com.evansitzes.game.entity.environment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.evansitzes.game.Configuration;
import com.evansitzes.game.TwilightEternal;
import com.evansitzes.game.entity.Entity;
import com.evansitzes.game.helpers.DrawUtils;

import static com.evansitzes.game.Configuration.ITEM_CAMPAIGN_SIZE;

public class EnvironmentItem extends Entity {

    public final TwilightEternal game;
    public final Rectangle pickupZoneRectangle;

    public EnvironmentItem(final TwilightEternal game) {
        super(game);
        this.game = game;
        this.pickupZoneRectangle = new Rectangle();
    }

    @Override
    public void handle(final float delta) {

    }

    @Override
    public void setSpritesPositions() {
//        final Configuration configuration = new Configuration();

        sprite.setPosition(this.position.x, this.position.y);
        sprite.setSize(ITEM_CAMPAIGN_SIZE, ITEM_CAMPAIGN_SIZE);
        this.hitBox.set(this.position.x + 5, this.position.y - 5, 20, 30);

        pickupZoneRectangle.set(this.position.x - Configuration.ITEM_CONVERSATION_RECTANGLE_OFFSET_X,
                this.position.y - Configuration.ITEM_CONVERSATION_RECTANGLE_OFFSET_Y,
                Configuration.ITEM_CONVERSATION_RECTANGLE_WIDTH,
                Configuration.ITEM_CONVERSATION_RECTANGLE_HEIGHT);
    }

    public boolean overlapsPickupZone(final Entity entity) {
        if (pickupZoneRectangle == null) { return false; }

        return pickupZoneRectangle.overlaps(entity.hitBox);
    }

    public void draw() {
        sprite.draw(game.batch);

        if (sprite.getX() == 0 && sprite.getY() == 0) {
            setSpritesPositions();
        }

        if (game.debug) {
            DrawUtils.draw(game, hitBox, Color.BLUE);
            DrawUtils.draw(game, pickupZoneRectangle, Color.PINK);
        }
    }
}

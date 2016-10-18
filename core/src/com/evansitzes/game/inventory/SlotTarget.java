package com.evansitzes.game.inventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;

/**
 * Created by evan on 9/27/16.
 */
public class SlotTarget extends Target {

    public SlotTarget(final SlotActor actor) {
        super(actor);
        getActor().setColor(Color.LIGHT_GRAY);
    }

    @Override
    public boolean drag(final Source source, final Payload payload, final float x, final float y, final int pointer) {
        final Slot payloadSlot = (Slot) payload.getObject();

        // in case we drag something over this target, we highlight it a bit
        getActor().setColor(Color.WHITE);

        // returning true means that this is a valid target
        return true;
    }

    @Override
    public void drop(final Source source, final Payload payload, final float x, final float y, final int pointer) {
        // we already handle all of this in dragStop in the Source
    }

    @Override
    public void reset(final Source source, final Payload payload) {
        getActor().setColor(Color.LIGHT_GRAY);
    }

}

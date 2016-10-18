package com.evansitzes.game.inventory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;

/**
 * Created by evan on 9/27/16.
 */
public class SlotSource extends Source {

    private Slot sourceSlot;

    public SlotSource(final SlotActor actor) {
        super(actor);
        this.sourceSlot = actor.getSlot();
    }

    @Override
    public Payload dragStart(final InputEvent event, final float x, final float y, final int pointer) {
        if (sourceSlot.getAmount() == 0) {
            return null;
        }

        final Payload payload = new Payload();
        final Slot payloadSlot = new Slot(sourceSlot.getItem(), sourceSlot.getAmount());
        sourceSlot.take(sourceSlot.getAmount());
        payload.setObject(payloadSlot);

        final TextureRegion icon = payloadSlot.getItem().getTextureRegion();

        final Actor dragActor = new Image(icon);
        payload.setDragActor(dragActor);

        final Actor validDragActor = new Image(icon);
        payload.setValidDragActor(validDragActor);

        final Actor invalidDragActor = new Image(icon);
        payload.setInvalidDragActor(invalidDragActor);

        return payload;
    }

    @Override
    public void dragStop(final InputEvent event, final float x, final float y, final int pointer, final Payload payload, final Target target) {
        final Slot payloadSlot = (Slot) payload.getObject();
        // the payload was dropped over a valid target
        if (target != null) {
            final Slot targetSlot = ((SlotActor) target.getActor()).getSlot();
            // if the item is the same, stack it
            if (targetSlot.getItem() == payloadSlot.getItem() || targetSlot.getItem() == null) {
                targetSlot.add(payloadSlot.getItem(), payloadSlot.getAmount());
            } else {
                // the item is not the same, thus switch the items
                final Item targetType = targetSlot.getItem();
                final int targetAmount = targetSlot.getAmount();
                targetSlot.take(targetAmount);
                targetSlot.add(payloadSlot.getItem(), payloadSlot.getAmount());
                sourceSlot.add(targetType, targetAmount);
            }
        } else {
            // the payload was not dropped over a target, thus put it back to where it came from
            sourceSlot.add(payloadSlot.getItem(), payloadSlot.getAmount());
        }
    }
}
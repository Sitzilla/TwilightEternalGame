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
        final Slot payloadSlot = new Slot(sourceSlot.getItem(), sourceSlot.getInventoryType(), sourceSlot.getAmount(), sourceSlot.isEquipment());
        sourceSlot.take(sourceSlot.getAmount());
        payload.setObject(payloadSlot);

        final TextureRegion icon = payloadSlot.getItem().texture;

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

            if (!isValidExchange(payloadSlot, targetSlot)) {
                sourceSlot.add(payloadSlot.getItem(), payloadSlot.getAmount());
                return;
            }

            // if the item is the same, stack it
            if (!targetSlot.isEquipment() && (targetSlot.getItem() == payloadSlot.getItem() || targetSlot.getItem() == null)) {
                if (equipmentIsChanged(payloadSlot, targetSlot)) {
                    ((SlotActor) target.getActor()).getCurrentScreen().switchItems(payloadSlot, targetSlot);
                }

                targetSlot.add(payloadSlot.getItem(), payloadSlot.getAmount());
            } else {
                // the item is not the same, thus switch the items
                final Item targetType = targetSlot.getItem();
                final int targetAmount = targetSlot.getAmount();

                if (equipmentIsChanged(payloadSlot, targetSlot)) {
                    ((SlotActor) target.getActor()).getCurrentScreen().switchItems(payloadSlot, targetSlot);
                }

                targetSlot.take(targetAmount);
                targetSlot.add(payloadSlot.getItem(), payloadSlot.getAmount());
                sourceSlot.add(targetType, targetAmount);
            }
        } else {
            // the payload was not dropped over a target, thus put it back to where it came from
            sourceSlot.add(payloadSlot.getItem(), payloadSlot.getAmount());
        }
    }

    // Check if the target slot itemtype is valid for the payload item and check if the
    // source slot itemtype is valid for the target item itemtype
    private boolean isValidExchange(final Slot payloadSlot, final Slot targetSlot) {
        if (payloadSlot.getItem().inventoryType != targetSlot.getInventoryType() &&
                targetSlot.getInventoryType() != InventoryTypeEnum.GENERAL) {
            return false;
        }

        if (targetSlot.getItem() != null &&
                payloadSlot.getInventoryType() != targetSlot.getItem().inventoryType &&
                payloadSlot.getInventoryType() != InventoryTypeEnum.GENERAL) {
            sourceSlot.add(payloadSlot.getItem(), payloadSlot.getAmount());
            return false;
        }

        return true;
    }

    private boolean equipmentIsChanged(final Slot payloadSlot, final Slot targetSlot) {
        return payloadSlot.isEquipment() || targetSlot.isEquipment();
    }
}
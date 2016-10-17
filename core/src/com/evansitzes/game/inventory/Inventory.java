package com.evansitzes.game.inventory;

import com.badlogic.gdx.utils.Array;

/**
 * Created by evan on 9/27/16.
 */
public class Inventory {

    private Array<Slot> slots;

    public Inventory(final int size) {
        slots = new Array<Slot>(size);
        for (int i = 0; i < size; i++) {
            slots.add(new Slot(null, 0));
        }

    }

    public void createRandomItems() {
//        // create some random items
//        for (final Slot slot : slots) {
//            slot.add(Item.values()[MathUtils.random(0, Item.values().length - 1)], 1);
//        }
//
//        // create a few random empty slots
//        for (int i = 0; i < 3; i++) {
//            final Slot randomSlot = slots.get(MathUtils.random(0, slots.size - 1));
//            randomSlot.take(randomSlot.getAmount());
//        }
    }

    public void createEquipment() {
        slots.get(0).add(new Item("bronze_sword"), 1);

    }

    public int checkInventory(final Item item) {
        int amount = 0;

        for (final Slot slot : slots) {
            if (slot.getItem() == item) {
                amount += slot.getAmount();
            }
        }

        return amount;
    }

    public boolean store(final Item item, final int amount) {
        // first check for a slot with the same item type
        final Slot itemSlot = firstSlotWithItem(item);
        if (itemSlot != null) {
            itemSlot.add(item, amount);
            return true;
        } else {
            // now check for an available empty slot
            final Slot emptySlot = firstSlotWithItem(null);
            if (emptySlot != null) {
                emptySlot.add(item, amount);
                return true;
            }
        }

        // no slot to add
        return false;
    }

    public Array<Slot> getSlots() {
        return slots;
    }

    private Slot firstSlotWithItem(final Item item) {
        for (final Slot slot : slots) {
            if (slot.getItem() == item) {
                return slot;
            }
        }

        return null;
    }

}
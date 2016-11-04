package com.evansitzes.game.inventory;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

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

    public void populateInventory(final ArrayList<String> equipment) {
        for (int i = 0; i < slots.size; i++) {
            if (equipment.get(i) != null) {
                slots.get(i).add(new Item(equipment.get(i)), 1);
                continue;
            }

            slots.get(i).add(new Item("blank"), 1);
        }
    }

    public void populateEquipment(final ArrayList<String> equipment) {
        for (int i = 0; i < slots.size; i++) {
            if (equipment.get(i) != null) {
                slots.get(i).add(new Item(equipment.get(i)), 1);
                continue;
            }

            slots.get(i).add(new Item("blank"), 1);
        }
    }

    public ArrayList<String> getItems() {
        final ArrayList<String> currentItems = new ArrayList<String>();

        for (int i = 0; i < slots.size; i++) {
            currentItems.add(slots.get(i).getItem().getName());
        }

        return currentItems;
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
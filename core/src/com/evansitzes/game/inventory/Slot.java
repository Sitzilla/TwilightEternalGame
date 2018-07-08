package com.evansitzes.game.inventory;

import com.badlogic.gdx.utils.Array;

/**
 * Created by evan on 9/27/16.
 */
public class Slot {

    private Item item;
    private InventoryTypeEnum inventoryType;
    private int amount;
    private boolean isEquipment;

    private Array<SlotListener> slotListeners = new Array<SlotListener>();

    public Slot(final Item item, final InventoryTypeEnum inventoryType, final int amount, final boolean isEquipment) {
        this.item = item;
        this.inventoryType = inventoryType;
        this.amount = amount;
        this.isEquipment = isEquipment;
    }

    public boolean isEmpty() {
        return item == null || amount <= 0;
    }

    public boolean add(final Item item, final int amount) {
        if (this.item == null || (this.item.name.equals(item.name) && item.isCombinable)) {
            this.item = item;
            this.amount += amount;
            notifyListeners();
            return true;
        }

        return false;
    }

    public boolean take(final int amount) {
        if (this.amount >= amount) {
            this.amount -= amount;
            if (this.amount == 0) {
                item = null;
            }
            notifyListeners();
            return true;
        }

        return false;
    }

    public void addListener(final SlotListener slotListener) {
        slotListeners.add(slotListener);
    }

    public void removeListener(final SlotListener slotListener) {
        slotListeners.removeValue(slotListener, true);
    }

    private void notifyListeners() {
        for (final SlotListener slotListener : slotListeners) {
            slotListener.hasChanged(this);
        }
    }

    public Item getItem() {
        return item;
    }

    public void setItem(final Item item, final int amount) {
        this.item = item;
        this.amount = amount;
        notifyListeners();
    }

    public int getAmount() {
        return amount;
    }

    public InventoryTypeEnum getInventoryType() {
        return inventoryType;
    }

    public boolean isEquipment() {
        return isEquipment;
    }

    @Override
    public String toString() {
        return "Slot[" + item + ":" + amount + "]";
    }
}
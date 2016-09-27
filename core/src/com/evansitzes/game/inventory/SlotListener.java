package com.evansitzes.game.inventory;

/**
 * Created by evan on 9/27/16.
 */
public interface SlotListener {

    /**
     * Will be called whenever the slot has changed.
     */
    void hasChanged(Slot slot);

}

package com.evansitzes.game.inventory;

import com.badlogic.gdx.utils.Array;
import com.evansitzes.game.model.ArticlesEnvelope;

import java.util.ArrayList;

/**
 * Created by evan on 5/13/17.
 */
public class CurrentStuff {

    protected Array<Slot> slots;
    protected ArticlesEnvelope articlesEnvelope;

    public ArrayList<String> getItems() {
        final ArrayList<String> currentItems = new ArrayList<String>();

        for (int i = 0; i < slots.size; i++) {
            if (slots.get(i).getItem() == null) {
                continue;
            }
            currentItems.add(slots.get(i).getItem().getName());
        }

        return currentItems;
    }

    public void removeItem(final Item item) {
        for (final Slot slot : slots) {
            if (slot.getItem() == item) {
                slot.setItem(null, 0);
            }
        }
    }
}

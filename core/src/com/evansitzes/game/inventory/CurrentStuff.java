package com.evansitzes.game.inventory;

import com.badlogic.gdx.utils.Array;
import com.evansitzes.game.model.ArticlesEnvelope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by evan on 5/13/17.
 */
public class CurrentStuff {

    protected Array<Slot> slots;
    protected ArticlesEnvelope articlesEnvelope;

    public Map<String, String> getEquipment() {
        Map<String, String> equipment = new HashMap<String, String>();

        equipment.put("helmet", (slots.get(0).getItem() != null) ? slots.get(0).getItem().getName() : null);
        equipment.put("armor", (slots.get(1).getItem() != null) ? slots.get(1).getItem().getName() : null);
        equipment.put("weapon", (slots.get(2).getItem() != null) ? slots.get(2).getItem().getName() : null);
        equipment.put("pants", (slots.get(3).getItem() != null) ? slots.get(3).getItem().getName() : null);
        equipment.put("shoes", (slots.get(4).getItem() != null) ? slots.get(4).getItem().getName() : null);

        return equipment;
    }

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

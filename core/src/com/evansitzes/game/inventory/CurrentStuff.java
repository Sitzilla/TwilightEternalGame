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
        final Map<String, String> equipment = new HashMap<String, String>();

        equipment.put("helmet", (slots.get(0).getItem() != null) ? slots.get(0).getItem().name : null);
        equipment.put("armor", (slots.get(1).getItem() != null) ? slots.get(1).getItem().name : null);
        equipment.put("weapon", (slots.get(2).getItem() != null) ? slots.get(2).getItem().name : null);
        equipment.put("pants", (slots.get(3).getItem() != null) ? slots.get(3).getItem().name : null);
        equipment.put("shoes", (slots.get(4).getItem() != null) ? slots.get(4).getItem().name : null);

        return equipment;
    }

    public ArrayList<String> getItems() {
        final ArrayList<String> currentItems = new ArrayList<String>();

        for (int i = 0; i < slots.size; i++) {
            if (slots.get(i).getItem() == null) {
                continue;
            }

            for (int j = 0; j < slots.get(i).getAmount(); j++) {
                currentItems.add(slots.get(i).getItem().name);
            }
        }

        return currentItems;
    }

    public void removeItem(final Item item) {
        for (final Slot slot : slots) {
            if (slot.getItem() != null && slot.getItem().name.equals(item.name)) {
                slot.take(1);
            }
        }
    }
}

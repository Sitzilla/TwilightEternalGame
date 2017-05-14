package com.evansitzes.game.inventory;

import com.badlogic.gdx.utils.Array;
import com.evansitzes.game.exceptions.ItemDoesntExistException;
import com.evansitzes.game.helpers.ItemTypeParser;
import com.evansitzes.game.helpers.YamlParser;
import com.evansitzes.game.model.Article;

import java.util.ArrayList;

/**
 * Created by evan on 5/13/17.
 */
public class CurrentEquipment extends CurrentStuff {

    public CurrentEquipment(final int size) {
        slots = new Array<Slot>(size);
        slots.add(new Slot(null, InventoryTypeEnum.HELMET, 0, true));
        slots.add(new Slot(null, InventoryTypeEnum.ARMOR, 0, true));
        slots.add(new Slot(null, InventoryTypeEnum.WEAPON, 0, true));
        slots.add(new Slot(null, InventoryTypeEnum.PANTS, 0, true));
        slots.add(new Slot(null, InventoryTypeEnum.SHOES, 0, true));

        articlesEnvelope = new YamlParser().loadItemMap();
    }

    public void populateEquipment(final ArrayList<String> equipment) {
        for (int i = 0; i < slots.size; i++) {
            if (equipment.get(i) != null) {
                final Article article = articlesEnvelope.getArticle(equipment.get(i));
                slots.get(i).add(new Item(article.getName(), ItemTypeParser.parse(article), article.getDescription()), 1);
                continue;
            }

//            slots.get(i).add(new Article("blank"), 1);
        }
    }

    public Slot getEquipmentSlotOfSpecificType(final InventoryTypeEnum inventoryType) {
        for (final Slot slot : slots) {
            if (slot.getInventoryType() == inventoryType) {
                return slot;
            }
        }

        throw new ItemDoesntExistException("Slot of type <" + inventoryType + "> does not exist.");
    }

    public Array<Slot> getSlots() {
        return slots;
    }

    public void addItem(final Item item) {
        for (final Slot slot : slots) {
            if (slot.getInventoryType() == item.getInventoryType()) {
                slot.add(item, 1);
                return;
            }
        }

        throw new ItemDoesntExistException("Slot of type <" + item.getInventoryType() + "> does not exist.");
    }
}

package com.evansitzes.game.inventory;

import com.badlogic.gdx.utils.Array;
import com.evansitzes.game.exceptions.ItemDoesntExistException;
import com.evansitzes.game.helpers.YamlParser;
import com.evansitzes.game.model.Article;

import java.util.Map;

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

    public void populateEquipment(final Map<String, String> equipment) {
        if (equipment.get("helmet") != null) {
            final Article article =  articlesEnvelope.getArticle(equipment.get("helmet"));
            slots.get(0).add(new Item(article), 1);
        }

        if (equipment.get("armor") != null) {
            final Article article =  articlesEnvelope.getArticle(equipment.get("armor"));
            slots.get(1).add(new Item(article), 1);
        }
        if (equipment.get("weapon") != null) {
            final Article article =  articlesEnvelope.getArticle(equipment.get("weapon"));
            slots.get(2).add(new Item(article), 1);
        }
        if (equipment.get("pants") != null) {
            final Article article =  articlesEnvelope.getArticle(equipment.get("pants"));
            slots.get(3).add(new Item(article), 1);
        }
        if (equipment.get("shoes") != null) {
            final Article article =  articlesEnvelope.getArticle(equipment.get("shoes"));
            slots.get(4).add(new Item(article), 1);
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
            if (slot.getInventoryType() == item.inventoryType) {
                slot.add(item, 1);
                return;
            }
        }

        throw new ItemDoesntExistException("Slot of type <" + item.inventoryType + "> does not exist.");
    }
}

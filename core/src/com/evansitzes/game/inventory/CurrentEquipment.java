package com.evansitzes.game.inventory;

import com.badlogic.gdx.utils.Array;
import com.evansitzes.game.exceptions.ItemDoesntExistException;
import com.evansitzes.game.helpers.ItemTypeParser;
import com.evansitzes.game.helpers.YamlParser;

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
            slots.get(0).add(new Item(articlesEnvelope.getArticle(equipment.get("helmet")).getName(), ItemTypeParser.parse(articlesEnvelope.getArticle(equipment.get("helmet"))), articlesEnvelope.getArticle(equipment.get("helmet")).getDescription()), 1);
        }

        if (equipment.get("armor") != null) {
            slots.get(1).add(new Item(articlesEnvelope.getArticle(equipment.get("armor")).getName(), ItemTypeParser.parse(articlesEnvelope.getArticle(equipment.get("armor"))), articlesEnvelope.getArticle(equipment.get("armor")).getDescription()), 1);
        }
        if (equipment.get("weapon") != null) {
            slots.get(2).add(new Item(articlesEnvelope.getArticle(equipment.get("weapon")).getName(), ItemTypeParser.parse(articlesEnvelope.getArticle(equipment.get("weapon"))), articlesEnvelope.getArticle(equipment.get("weapon")).getDescription()), 1);
        }
        if (equipment.get("pants") != null) {
            slots.get(3).add(new Item(articlesEnvelope.getArticle(equipment.get("pants")).getName(), ItemTypeParser.parse(articlesEnvelope.getArticle(equipment.get("pants"))), articlesEnvelope.getArticle(equipment.get("pants")).getDescription()), 1);
        }
        if (equipment.get("shoes") != null) {
            slots.get(4).add(new Item(articlesEnvelope.getArticle(equipment.get("shoes")).getName(), ItemTypeParser.parse(articlesEnvelope.getArticle(equipment.get("shoes"))), articlesEnvelope.getArticle(equipment.get("shoes")).getDescription()), 1);
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

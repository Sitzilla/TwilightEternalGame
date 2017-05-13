package com.evansitzes.game.inventory;

import com.badlogic.gdx.utils.Array;
import com.evansitzes.game.helpers.ItemTypeParser;
import com.evansitzes.game.helpers.YamlParser;
import com.evansitzes.game.model.Article;
import com.evansitzes.game.model.ArticlesEnvelope;

import java.util.ArrayList;

/**
 * Created by evan on 9/27/16.
 */
public class Inventory {

    private Array<Slot> slots;
    private ArticlesEnvelope articlesEnvelope;

    public Inventory(final int size, final String type) {

        // TODO this is currently hardcoded
        if (type.equals("inventory")) {
            slots = new Array<Slot>(size);
            for (int i = 0; i < size; i++) {
                slots.add(new Slot(null, InventoryTypeEnum.GENERAL, 0, false));
            }
        } else {
            slots = new Array<Slot>(size);
            slots.add(new Slot(null, InventoryTypeEnum.HELMET, 0, true));
            slots.add(new Slot(null, InventoryTypeEnum.ARMOR, 0, true));
            slots.add(new Slot(null, InventoryTypeEnum.WEAPON, 0, true));
            slots.add(new Slot(null, InventoryTypeEnum.PANTS, 0, true));
            slots.add(new Slot(null, InventoryTypeEnum.SHOES, 0, true));
        }

        articlesEnvelope = new YamlParser().loadItemMap();
        System.out.println(articlesEnvelope);
    }

    public void populateInventory(final ArrayList<String> equipment) {
        for (int i = 0; i < slots.size; i++) {

           try {
               if (equipment.get(i) != null) {
                   final Article article = articlesEnvelope.getArticle(equipment.get(i));
                   slots.get(i).add(new Item(article.getName(), ItemTypeParser.parse(article), article.getDescription()), 1);
               }

           } catch (IndexOutOfBoundsException e) {
           }
        }
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
        // TODO activate this when we have combination logic
//        final Slot itemSlot = firstSlotWithItem(item);
//        if (itemSlot != null) {
//            itemSlot.add(item, amount);
//            return true;
//        }

        // now check for an available empty slot
        final Slot emptySlot = firstSlotWithItem(null);
        if (emptySlot != null) {
            emptySlot.add(item, amount);
            return true;
        }

        // no slot to add
        return false;
    }

    public void removeItem(final Item item) {
        for (final Slot slot : slots) {
            if (slot.getItem() == item) {
                slot.setItem(null, 0);
            }
        }
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
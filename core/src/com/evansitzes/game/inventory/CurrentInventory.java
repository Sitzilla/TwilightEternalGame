package com.evansitzes.game.inventory;

import com.badlogic.gdx.utils.Array;
import com.evansitzes.game.helpers.YamlParser;
import com.evansitzes.game.model.Article;

import java.util.ArrayList;

/**
 * Created by evan on 9/27/16.
 */
public class CurrentInventory extends CurrentStuff {

    public CurrentInventory(final int size) {
        slots = new Array<Slot>(size);
        for (int i = 0; i < size; i++) {
            slots.add(new Slot(null, InventoryTypeEnum.GENERAL, 0, false));
        }

        articlesEnvelope = new YamlParser().loadItemMap();
    }

    public void populateInventory(final ArrayList<String> equipment) {
        for (final String item : equipment) {
            final Article article = articlesEnvelope.getArticle(item);
            final Item singleItem = new Item(article);
            store(singleItem, 1);
        }


//        for (int i = 0; i < slots.size; i++) {
//
//           try {
//               if (equipment.get(i) != null) {
//                   final Article article = articlesEnvelope.getArticle(equipment.get(i));
//                   slots.get(i).add(new Item(article.getName(), ItemTypeParser.parse(article), article.getDescription()), 1);
//               }
//
//           } catch (final IndexOutOfBoundsException e) {
//           }
//        }
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

        if (item.isCombinable) {
            final Slot itemSlot = firstSlotWithItem(item);
            if (itemSlot != null) {
                itemSlot.add(item, amount);
                return true;
            }
        }

        // now check for an available empty slot
        final Slot emptySlot = firstEmptySlot();
        if (emptySlot != null) {
            emptySlot.add(item, amount);
            return true;
        }

        // no slot to add
        return false;
    }

    public Array<Slot> getSlots() {
        return slots;
    }

    private Slot firstSlotWithItem(final Item item) {
        for (final Slot slot : slots) {
            if (slot.getItem() != null
                    && slot.getItem().name.equals(item.name)) {
                return slot;
            }
        }

        return null;
    }

    private Slot firstEmptySlot() {
        for (final Slot slot : slots) {
            if (slot.getItem() == null) {
                return slot;
            }
        }

        return null;
    }

}
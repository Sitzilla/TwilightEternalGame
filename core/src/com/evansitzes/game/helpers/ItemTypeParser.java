package com.evansitzes.game.helpers;

import com.evansitzes.game.inventory.InventoryTypeEnum;
import com.evansitzes.game.model.Article;

/**
 * Returns the item type enum based on the string item type
 */
public class ItemTypeParser {

    public static InventoryTypeEnum parse(Article article) {

        if (article.getItemType().equals("helmet")) {
            return InventoryTypeEnum.HELMET;
        }

        if (article.getItemType().equals("armor")) {
            return InventoryTypeEnum.ARMOR;
        }

        if (article.getItemType().equals("weapon")) {
            return InventoryTypeEnum.WEAPON;
        }

        if (article.getItemType().equals("pants")) {
            return InventoryTypeEnum.PANTS;
        }

        if (article.getItemType().equals("shoes")) {
            return InventoryTypeEnum.SHOES;
        }

        return InventoryTypeEnum.GENERAL;
    }


}

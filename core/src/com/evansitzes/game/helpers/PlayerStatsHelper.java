package com.evansitzes.game.helpers;

import com.evansitzes.game.entity.team.Player;
import com.evansitzes.game.inventory.Item;
import com.evansitzes.game.model.ArticlesEnvelope;

import java.util.HashMap;

/**
 * Created by evan on 5/7/17.
 */
public class PlayerStatsHelper {

    private static ArticlesEnvelope articlesEnvelope = new YamlParser().loadItemMap();

    public static void buildPlayerStats(final Player player) {
        for (String key : player.equipment.keySet()) {
            if (player.equipment.get(key) != null) {
                add(player, player.equipment.get(key));
            }
        }


        player.updateTotalStats();
    }

    public static void switchPlayerEquipment(final Player player, final Item itemRemoved, final Item itemAdded) {
        removePlayerEquipment(player, itemRemoved);
        addPlayerEquipment(player, itemAdded);
    }

    public static void removePlayerEquipment(final Player player, final Item item) {
        remove(player, item.name);
        player.updateTotalStats();
    }

    public static void addPlayerEquipment(final Player player, final Item item) {
        add(player, item.name);
        player.updateTotalStats();
    }

    private static void add(final Player player, final String item) {
        final HashMap<String, Integer> attributes = articlesEnvelope.getArticle(item).getAttributes();

        if (attributes == null) {
            return;
        }

        // TODO how do we want to handle where these are stored/configured?
        player.strengthModifier += attributes.get("strength");
        player.speedModifier += attributes.get("speed");
        player.intelligenceModifier += attributes.get("intelligence");
        player.hitPointsModifier += attributes.get("hitPoints");
        player.magicPointsModifier += attributes.get("magicPoints");
        player.armorModifier += attributes.get("armor");
        player.resistanceModifier += attributes.get("resistance");
    }

    private static void remove(final Player player, final String item) {
        final HashMap<String, Integer> attributes = articlesEnvelope.getArticle(item).getAttributes();

        if (attributes == null) {
            return;
        }

        player.strengthModifier -= attributes.get("strength");
        player.speedModifier -= attributes.get("speed");
        player.intelligenceModifier -= attributes.get("intelligence");
        player.hitPointsModifier -= attributes.get("hitPoints");
        player.magicPointsModifier -= attributes.get("magicPoints");
        player.armorModifier -= attributes.get("armor");
        player.resistanceModifier -= attributes.get("resistance");
    }
}

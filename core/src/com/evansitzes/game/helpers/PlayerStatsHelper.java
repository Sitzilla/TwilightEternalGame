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
        for (final String item : player.equipment) {
            add(player, item);
        }

        player.updateTotalAttributes();
    }

    public static void switchPlayerEquipment(final Player player, final Item itemRemoved, final Item itemAdded) {
        removePlayerEquipment(player, itemRemoved);
        addPlayerEquipment(player, itemAdded);
    }

    public static void removePlayerEquipment(final Player player, final Item item) {
        remove(player, item.getName());
        player.updateTotalAttributes();
    }

    public static void addPlayerEquipment(final Player player, final Item item) {
        add(player, item.getName());
        player.updateTotalAttributes();
    }

    private static void add(Player player, String item) {
        final HashMap<String, Integer> attributes = articlesEnvelope.getArticle(item).getAttributes();

        if (attributes == null) {
            return;
        }

        // TODO how do we want to handle where these are stored/configured?
        player.strengthModifier += attributes.get("strength");
        player.dexterityModifier += attributes.get("dexterity");
        player.constitutionModifier += attributes.get("constitution");
        player.wisdomModifier += attributes.get("wisdom");
        player.intelligenceModifier += attributes.get("intelligence");
        player.charismaModifier += attributes.get("charisma");
    }

    private static void remove(Player player, String item) {
        final HashMap<String, Integer> attributes = articlesEnvelope.getArticle(item).getAttributes();

        if (attributes == null) {
            return;
        }

        player.strengthModifier -= attributes.get("strength");
        player.dexterityModifier -= attributes.get("dexterity");
        player.constitutionModifier -= attributes.get("constitution");
        player.wisdomModifier -= attributes.get("wisdom");
        player.intelligenceModifier -= attributes.get("intelligence");
        player.charismaModifier -= attributes.get("charisma");
    }
}
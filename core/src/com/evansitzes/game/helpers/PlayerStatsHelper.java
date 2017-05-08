package com.evansitzes.game.helpers;

import com.evansitzes.game.entity.team.Player;
import com.evansitzes.game.model.ArticlesEnvelope;

import java.util.HashMap;

/**
 * Created by evan on 5/7/17.
 */
public class PlayerStatsHelper {

    public static void buildPlayerStats(final Player player) {
        final ArticlesEnvelope articlesEnvelope = new YamlParser().loadItemMap();

        for (final String item : player.equipment) {
            final HashMap<String, Integer> attributes = articlesEnvelope.getArticle(item).getAttributes();

            if (attributes == null) {
                continue;
            }

            // TODO how do we want to handle where these are stored/configured?
            player.strengthModifier += attributes.get("strength");
            player.dexterityModifier += attributes.get("dexterity");
            player.constitutionModifier += attributes.get("constitution");
            player.wisdomModifier += attributes.get("wisdom");
            player.intelligenceModifier += attributes.get("intelligence");
            player.charismaModifier += attributes.get("charisma");
        }

        player.updateTotalAttributes();
    }
}

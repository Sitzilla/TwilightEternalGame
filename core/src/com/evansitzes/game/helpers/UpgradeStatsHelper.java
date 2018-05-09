package com.evansitzes.game.helpers;

import com.evansitzes.game.entity.team.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UpgradeStatsHelper {

    public static Map<String, Integer> calculateNextLevelStats(final Player player) {
        final Map<String, Integer> stats = new HashMap<String, Integer>();
        final int nextLevel = LevelCalculator.nextLevel(player.level);

        stats.put("strength", calculateStrength());
        stats.put("speed", calculateSpeed());
        stats.put("intelligence", calculateIntelligence());
        stats.put("hitPoints", calculateHitPoints());
        stats.put("magicPoints", calculateMagicPoints());

        return stats;
    }

    private static int calculateStrength() {
        final Random rand = new Random();
        return rand.nextInt(3) + 1;
    }

    private static int calculateSpeed() {
        final Random rand = new Random();
        return rand.nextInt(2) + 1;
    }

    private static int calculateIntelligence() {
        final Random rand = new Random();
        return rand.nextInt(1) + 1;
    }

    private static int calculateHitPoints() {
        final Random rand = new Random();
        return rand.nextInt(3) + 1;
    }

    private static int calculateMagicPoints() {
        final Random rand = new Random();
        return rand.nextInt(1) + 1;
    }
}

package com.evansitzes.game.helpers;

public class LevelCalculator {

    public static int nextLevel(final int level) {
        return (4 * cube(level + 1)) / 5;
    }

    private static int cube(final int x) {
        return x * x * x;
    }
}

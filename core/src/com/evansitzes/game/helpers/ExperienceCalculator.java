package com.evansitzes.game.helpers;

import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.entity.team.TeamMember;

import java.util.Random;

public class ExperienceCalculator {

    public static int MINIMUM_EXPERIENCE = 5;
    public static int MAXIMUM_EXPERIENCE = 10;

    public static int calculateExperience(final TeamMember teamMember, final Enemy enemy) {
        Random rand = new Random();

        int baseExperience = rand.nextInt((MAXIMUM_EXPERIENCE - MINIMUM_EXPERIENCE) + 1) + MINIMUM_EXPERIENCE;
        int modifiedExperience = baseExperience * enemy.difficultyClass;

        // level-related modification logic

        return 5;
    }
}

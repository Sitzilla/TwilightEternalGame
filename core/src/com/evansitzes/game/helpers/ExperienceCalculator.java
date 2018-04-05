package com.evansitzes.game.helpers;

import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.entity.team.TeamMember;

import java.util.List;

public class ExperienceCalculator {

    public static int calculateExperience(final TeamMember teamMember, final List<Enemy> enemies) {
        float totalExperience = 0;

        for (final Enemy enemy : enemies) {
            totalExperience += enemy.difficultyClass + (float) enemy.level;
        }

        return (int) totalExperience;
    }
}

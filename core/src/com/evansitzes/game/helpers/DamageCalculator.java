package com.evansitzes.game.helpers;

import com.evansitzes.game.entity.Entity;

/**
 * Created by evan on 5/7/17.
 */
public class DamageCalculator {

    public static int calculatePhysicalDamage(final Entity attacker, final Entity receiver) {
        final int attackerDamage = attacker.baseStrength;
        final int receiverDefence = receiver.totalArmorClass;

        return attackerDamage - receiverDefence;
    }
}
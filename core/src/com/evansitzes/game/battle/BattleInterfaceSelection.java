package com.evansitzes.game.battle;

/**
 * Created by evan on 5/6/17.
 */
public class BattleInterfaceSelection {
    public final String name;
    public final BattleChoiceEnum choice;

    public BattleInterfaceSelection(final String name, final BattleChoiceEnum choice) {
        this.name = name;
        this.choice = choice;
    }
}

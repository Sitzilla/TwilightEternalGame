package com.evansitzes.game.battle;

import java.util.List;

/**
 * Created by evan on 5/13/17.
 */
public class BattleSelectionPath {
    public List<BattleInterfaceSelection> battleInterfaceSelections;
    public final String back;
    public final boolean isEnd;
    public final boolean isOffensive;

    public BattleSelectionPath(final List<BattleInterfaceSelection> battleInterfaceSelections, final String back, final boolean isEnd, final boolean isOffensive) {
        this.battleInterfaceSelections = battleInterfaceSelections;
        this.back = back;
        this.isEnd = isEnd;
        this.isOffensive = isOffensive;
    }
}

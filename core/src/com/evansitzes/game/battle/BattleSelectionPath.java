package com.evansitzes.game.battle;

/**
 * Created by evan on 5/13/17.
 */
public class BattleSelectionPath {
    public BattleInterfaceData battleInterfaceData;
    public final String back;
    public final boolean isEnd;
    public final boolean isOffensive;

    public BattleSelectionPath(BattleInterfaceData battleInterfaceData, String back, boolean isEnd, boolean isOffensive) {
        this.battleInterfaceData = battleInterfaceData;
        this.back = back;
        this.isEnd = isEnd;
        this.isOffensive = isOffensive;
    }
}

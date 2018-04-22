package com.evansitzes.game.popups;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.evansitzes.game.entity.team.Player;

public class AttributesTable extends Table {

    public AttributesTable(final Player player, final Skin skin) {
        super(skin);
        this.setVisible(true);

        this.add(new Label("Strength:", skin)).left();
        this.add(new Label(Integer.toString(player.totalStrength), skin));
        this.row();

        this.add(new Label("Speed:", skin)).left();
        this.add(new Label(Integer.toString(player.totalSpeed), skin));
        this.row();

        this.add(new Label("Intelligence:", skin)).left();
        this.add(new Label(Integer.toString(player.totalIntelligence), skin));
        this.row();

        this.add(new Label("Armor:", skin)).left();
        this.add(new Label(Integer.toString(player.totalArmor), skin));
        this.row();

        this.add(new Label("Resistance:", skin)).left();
        this.add(new Label(Integer.toString(player.totalResistance), skin));
    }
}

package com.evansitzes.game;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.evansitzes.game.entity.team.Player;

public class AttributesTable extends Table {

    public AttributesTable(final Player player, final Skin skin) {
        super(skin);
        this.setVisible(true);

        this.add(new Label(player.name, skin)).center().colspan(2);
        this.row();
        this.add(new Label(String.format("Level:   %d", player.level), skin)).center().colspan(2);
        this.row();
        this.add("");
        this.row();

        this.add(new Label("Strength:", skin)).left();
        this.add(new Label(Integer.toString(player.totalStrength), skin));
        this.row();

        this.add(new Label("Dexterity:", skin)).left();
        this.add(new Label(Integer.toString(player.totalDexterity), skin));
        this.row();

        this.add(new Label("Constitution:", skin)).left();
        this.add(new Label(Integer.toString(player.totalConstitution), skin));
        this.row();

        this.add(new Label("Wisdom:", skin)).left();
        this.add(new Label(Integer.toString(player.totalWisdom), skin));
        this.row();

        this.add(new Label("Intelligence:", skin)).left();
        this.add(new Label(Integer.toString(player.totalIntelligence), skin));
        this.row();

        this.add(new Label("Charisma:", skin)).left();
        this.add(new Label(Integer.toString(player.totalCharisma), skin));
    }
}

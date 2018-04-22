package com.evansitzes.game.popups;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.evansitzes.game.entity.team.Player;

public class PlayerSummaryTable extends Table {

    public PlayerSummaryTable(final Player player, final Skin skin) {
        super(skin);
        this.setVisible(true);

        this.add(new Label(player.name, skin)).center().colspan(2);
        this.row();
        this.add(new Label(String.format("Level:   %d", player.level), skin)).center().colspan(2);
        this.row();
        this.add("");
        this.row();
    }
}

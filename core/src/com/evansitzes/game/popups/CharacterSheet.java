package com.evansitzes.game.popups;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.Configuration;
import com.evansitzes.game.entity.team.Player;
import com.evansitzes.game.helpers.LevelCalculator;
import com.evansitzes.game.helpers.Textures.Colors;
import com.evansitzes.game.helpers.Textures.People;

public class CharacterSheet extends Table {


    public CharacterSheet(final Player player, final Skin skin) {
        super(skin);

        this.setPosition(0, (float) (Configuration.HEIGHT / 2.5));
        this.setWidth((float) (Configuration.WIDTH / 3.5));
        this.setHeight((float) (Configuration.HEIGHT / 2.5));
        this.setVisible(true);
//        this.setDebug(true);

        this.background(new TextureRegionDrawable(Colors.GREY));

        final Image profilePicture = new Image();
        profilePicture.setDrawable(new TextureRegionDrawable(People.PLAYER_PORTRAIT));
        this.add(profilePicture).maxSize(250);

        final Table summaryTable = new Table();
        summaryTable.add(new PlayerSummaryTable(player, skin));
        summaryTable.row();
        summaryTable.add(new AttributesTable(player, skin));
        this.add(summaryTable).size(150);
        this.row();

        this.add(String.format("HP %d/%d", (int) player.currentHitPoints, (int) player.totalHitPoints));
        this.add(String.format("Experience:  %d", player.experience));
        this.row();
        this.add(String.format("MP %d/%d", (int) player.currentMagicPoints, (int) player.totalMagicPoints));
        this.add(String.format("Next Level:  %d", LevelCalculator.nextLevel(player.level)));
        this.row();

        final TextButton button = new TextButton("Done", skin);

        this.add(button).center().colspan(2);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                remove();
            }

        });


    }
}

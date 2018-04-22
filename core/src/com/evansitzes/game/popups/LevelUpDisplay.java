package com.evansitzes.game.popups;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.Configuration;
import com.evansitzes.game.entity.team.Player;
import com.evansitzes.game.helpers.LevelCalculator;
import com.evansitzes.game.helpers.Textures;

import java.util.Map;

import static com.evansitzes.game.helpers.UpgradeStatsHelper.calculateNextLevelStats;

public class LevelUpDisplay extends Table {

    public LevelUpDisplay(final Player player, final Skin skin) {
        super(skin);

        final float width = (float) (Configuration.WIDTH / 1.5);

        this.setPosition((float) (Configuration.WIDTH / 6.0), (float) (Configuration.HEIGHT / 6.0));
        this.setWidth(width);
        this.setHeight((float) (Configuration.HEIGHT / 1.5));
        this.setVisible(true);
        this.setDebug(true);

        this.background(new TextureRegionDrawable(Textures.Colors.GREY));

        this.add(new AttributesTable(player, skin)).width(width / 4);

        final Table rightsideTable = new Table(skin);

        rightsideTable.add(new PlayerSummaryTable(player, skin));
        rightsideTable.row();
        rightsideTable.add(String.format("Experience:  %d", player.experience));
        rightsideTable.row();
        rightsideTable.add(String.format("Next Level:  %d", LevelCalculator.nextLevel(player.level)));
        rightsideTable.row();

        final TextButton levelUpButton = new TextButton("Level Up!", skin);

        rightsideTable.add(levelUpButton);
        rightsideTable.row();

        this.add(rightsideTable).width((float) (width * 0.75));
//        this.add(new AttributesTable(player, skin)).size(150);
//        this.add(new PlayerSummaryTable(player, skin)).size(300);


        levelUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                if (player.experience > LevelCalculator.nextLevel(player.level)) {
                    System.out.println("Leveling up");
                    Map<String, Integer> stats = calculateNextLevelStats(player);
                }
            }

        });

    }


}

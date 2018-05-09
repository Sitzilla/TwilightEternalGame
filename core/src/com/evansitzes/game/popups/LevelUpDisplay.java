package com.evansitzes.game.popups;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

        buildScreen(player, skin);

//        this.add(new AttributesTable(player, skin)).size(150);
//        this.add(new PlayerSummaryTable(player, skin)).size(300);

    }

    private void buildScreen(final Player player, final Skin skin) {
        this.clear();

        final float width = (float) (Configuration.WIDTH / 1.5);

        this.setPosition((float) (Configuration.WIDTH / 6.0), (float) (Configuration.HEIGHT / 6.0));
        this.setWidth(width);
        this.setHeight((float) (Configuration.HEIGHT / 1.5));
        this.setVisible(true);
        this.setDebug(true);

        this.background(new TextureRegionDrawable(Textures.Colors.GREY));

        final Table leftsideTable = new Table(skin);
        leftsideTable.add(new AttributesTable(player, skin));
        leftsideTable.row();

        leftsideTable.add(new Label("HP:", skin)).left();
        leftsideTable.add(new Label(Integer.toString(player.totalHitPoints), skin));
        leftsideTable.row();

        leftsideTable.add(new Label("MP:", skin)).left();
        leftsideTable.add(new Label(Integer.toString(player.totalMagicPoints), skin));
        leftsideTable.row();

        this.add(leftsideTable).width(width / 4);

        final Table rightsideTable = new Table(skin);

        rightsideTable.add(new PlayerSummaryTable(player, skin));
        rightsideTable.row();
        rightsideTable.add(String.format("Experience:  %d", player.experience));
        rightsideTable.row();
        rightsideTable.add(String.format("Next Level:  %d", LevelCalculator.nextLevel(player.level)));
        rightsideTable.row();

        if (canUpgrade(player)) {
            final TextButton levelUpButton = new TextButton("Level Up!", skin);
            rightsideTable.add(levelUpButton);

            levelUpButton.addListener(new ClickListener() {
                @Override
                public void clicked(final InputEvent event, final float x, final float y) {
                    if (canUpgrade(player)) {
                        final Map<String, Integer> stats = calculateNextLevelStats(player);
                        System.out.println("Leveling up");
                        System.out.println(stats);

                        player.addStats(stats);
                        player.level += 1;
                        buildScreen(player, skin);
                    }
                }

            });
        } else {
            rightsideTable.add("");
        }

        rightsideTable.row();

        this.add(rightsideTable).width((float) (width * 0.75));
    }

    private static boolean canUpgrade(final Player player) {
        return player.experience >= LevelCalculator.nextLevel(player.level);
    }

}

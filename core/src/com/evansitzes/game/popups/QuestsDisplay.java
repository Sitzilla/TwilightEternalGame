package com.evansitzes.game.popups;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.Configuration;
import com.evansitzes.game.entity.team.Player;
import com.evansitzes.game.helpers.Textures.Colors;
import com.evansitzes.game.helpers.YamlParser;
import com.evansitzes.game.model.Quest;
import com.evansitzes.game.model.QuestsEnvelope;

public class QuestsDisplay extends Table {


    public QuestsDisplay(final Player player, final Skin skin) {
        super(skin);
        final QuestsEnvelope questsEnvelope = new YamlParser().loadQuestsEnvelope();
        final TextButton button = new TextButton("Done", skin);

        this.setPosition(0, (float) (Configuration.HEIGHT / 2.5));
        this.setWidth((float) (Configuration.WIDTH / 3.5));
        this.setHeight((float) (Configuration.HEIGHT / 2.5));
        this.setVisible(true);
//        this.setDebug(true);

        this.background(new TextureRegionDrawable(Colors.GREY));

        for (final String questName : player.quests) {
            final Quest quest = questsEnvelope.getQuest(questName);
            this.add(quest.getTitle());
            this.row();
        }

        this.add(button).center().colspan(2);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                remove();
            }
        });
    }
}

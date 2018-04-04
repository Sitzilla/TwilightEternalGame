package com.evansitzes.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.entity.team.Player;
import com.evansitzes.game.helpers.Textures;

public class CharacterSheet extends Table {


    public CharacterSheet(Player player, final Skin skin) {
        super(skin);

        this.setPosition(0, (float) (Configuration.HEIGHT / 2.5));
        this.setWidth((float) (Configuration.WIDTH / 3.5));
        this.setHeight((float) (Configuration.HEIGHT / 2.5));
        this.setVisible(true);

        this.background(new TextureRegionDrawable(Textures.Colors.GREY));

        final Image profilePicture = new Image();
        profilePicture.setDrawable(new TextureRegionDrawable(Textures.People.PLAYER_PORTRAIT));
        this.add(profilePicture).maxSize(250);

        final AttributesTable attributesTable = new AttributesTable(player, skin);

        this.add(attributesTable).size(150);
        this.row();

        this.add(String.format("Armor:  %d", player.totalArmor));
        this.add(String.format("Experience:  %d", player.experience));
        this.row();
        this.add(String.format("HP %d/%d", (int) player.currentHitPoints, (int) player.maxHitPoints));
        this.add("Next Level:  1000");
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

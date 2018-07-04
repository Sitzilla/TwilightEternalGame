package com.evansitzes.game.popups;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.Configuration;
import com.evansitzes.game.helpers.Textures;
import com.evansitzes.game.screens.TwilightEternalScreen;

public class ManagementDisplay extends Table {

    public ManagementDisplay(final TwilightEternalScreen currentScreen, final Skin skin) {
        super(skin);

        this.setWidth(100);
        this.setHeight(100);
        this.setVisible(true);
        this.setPosition(10, Configuration.HEIGHT - 75);

        defaults().space(8);
        row().fill().expandX();

        final ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        final TextureRegion image;
        image = Textures.Items.BACKPACK;

        style.imageUp = new TextureRegionDrawable(image);
        style.imageDown = new TextureRegionDrawable(image);

        ImageButton button1 = new ImageButton(style);

        final ImageButton.ImageButtonStyle style2 = new ImageButton.ImageButtonStyle();
        final TextureRegion image2;
        image2 = Textures.Items.SPELLS;
        style2.imageUp = new TextureRegionDrawable(image2);
        style2.imageDown = new TextureRegionDrawable(image2);
        ImageButton button2 = new ImageButton(style2);

        final ImageButton.ImageButtonStyle style3 = new ImageButton.ImageButtonStyle();
        final TextureRegion image3;
        image3 = Textures.Items.SAVE;
        style3.imageUp = new TextureRegionDrawable(image3);
        style3.imageDown = new TextureRegionDrawable(image3);
        ImageButton button3 = new ImageButton(style3);

        this.add(button1).height(50).width(Value.percentWidth(.50F, this));
        this.add(button2).height(50).width(Value.percentWidth(.50F, this));
//        this.row();
        this.add(button3).height(50).width(Value.percentWidth(.50F, this));

        button1.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                currentScreen.setInventoryScreen();
            }

        });

        button2.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                currentScreen.setConversationWindow("Spell functionality coming soon!");
            }

        });

        button3.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                currentScreen.setConversationWindow("Game Saved");
                currentScreen.game.savePlayerState();
            }

        });

        pack();
    }
}

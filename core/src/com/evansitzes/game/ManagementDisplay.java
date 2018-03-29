package com.evansitzes.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.helpers.Textures;
import com.evansitzes.game.screens.TwilightEternalScreen;

public class ManagementDisplay extends Table {

    public ManagementDisplay(final TwilightEternalScreen currentScreen, final Skin skin) {
        super(skin);

        this.setWidth(100);
        this.setHeight(100);
        this.setVisible(true);
        this.setPosition(10, Configuration.HEIGHT - 110);

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

        this.add(button1).width(Value.percentWidth(.50F, this));
        this.add(button2).width(Value.percentWidth(.50F, this));
        this.row();

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

        pack();
    }
}

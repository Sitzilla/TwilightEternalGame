package com.evansitzes.game.icons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.evansitzes.game.screens.TwilightEternalScreen;

/**
 * Created by evan on 11/19/16.
 */
public class IconActor extends Window {

    public IconActor(final TwilightEternalScreen currentScreen, final Icon icon, final Skin skin) {
        super("", skin);

        // basic layout
        defaults().space(8);
        row().fill().expandX();

        add(icon);

        icon.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                System.out.println("Opening inventory~");
                if (icon.getName().equals("backpack")) {
                    currentScreen.setInventoryScreen();
                } else {
                    currentScreen.setConversationWindow("Spell functionality coming soon!");
                }
            }

        });

        pack();
    }

}

package com.evansitzes.game.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by evan on 9/22/16.
 */
public class ConversationUI extends Dialog {

    public ConversationUI(Stage stage) {
        super("", new Skin(Gdx.files.internal("skins/golden-ui-skin.json")));
//        this.getTitleTable().setBackground(new Drawable("default-window-title"));

        this.add();


    }
}

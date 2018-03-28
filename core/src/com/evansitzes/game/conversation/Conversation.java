package com.evansitzes.game.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.evansitzes.game.GameflowController;

/**
 * Created by evan on 9/22/16.
 */
public class Conversation extends Dialog {

    private boolean seeWares;
    private String npcName;
    private GameflowController gameflowController;

    //TODO move gameflow logic out of conversation
    public Conversation(final String npcName, final boolean isInteractive, final GameflowController gameflowController) {
        super("", new Skin(Gdx.files.internal("skins/james/plain-james-ui.json")));

        setMovable(false);
        setResizable(false);
        this.gameflowController = gameflowController;
//            Label label = new Label("", skin);
//            label.setWrap(true);
//            label.setFontScale(.8f);
//            label.setAlignment(Align.center);

        padTop(50).padBottom(50);
//            getContentTable().add(label).WIDTH(250).row();
        getButtonTable().padTop(50);
        setHeight(100);
        setWidth(150);
        this.npcName = npcName;
//            TextButton dbutton = new TextButton("Yes", skin);

        key(Keys.SPACE, false);
        key(Keys.ENTER, false);
        key(Keys.RIGHT, false);
        key(Keys.UP, false);
        key(Keys.DOWN, false);
        key(Keys.LEFT, false);

        if (isInteractive) {
            button("yes", true);
            key(Keys.ENTER, true);
            button("no", false);
        }
//            invalidateHierarchy();
//            invalidate();
//            layout();
    }

    @Override
    public float getPrefWidth() {
        // force dialog WIDTH
        return 800f;
    }

    @Override
    public float getPrefHeight() {
        // force dialog HEIGHT
        return 400f;
    }

    @Override
    public void result(final Object object) {
        seeWares = Boolean.parseBoolean(object.toString());

        if (seeWares) {
            gameflowController.setShopScreen(npcName);
        }

    }

    public void setText(final String text) {
        this.text(text);
    }

    public boolean isSeeWares() {
        return seeWares;
    }
}
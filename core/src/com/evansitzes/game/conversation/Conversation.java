package com.evansitzes.game.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by evan on 9/22/16.
 */
public class Conversation extends Dialog {

    private boolean seeWares = false;

    public Conversation(final boolean isInteractive) {
//        super("", new Skin(Gdx.files.internal("skins/golden-ui-skin.json")));
        super("", new Skin(Gdx.files.internal("skins/james/plain-james-ui.json")));

        setMovable(false);
        setResizable(false);

//            Label label = new Label("", skin);
//            label.setWrap(true);
//            label.setFontScale(.8f);
//            label.setAlignment(Align.center);

        padTop(50).padBottom(50);
//            getContentTable().add(label).width(250).row();
        getButtonTable().padTop(50);
        setHeight(100);
        setWidth(150);

//            TextButton dbutton = new TextButton("Yes", skin);
        if (isInteractive) {
            button("yes", true);
            button("no", false);
        }

        key(Input.Keys.SPACE, false);
        key(Input.Keys.ENTER, false);
        key(Input.Keys.RIGHT, false);
        key(Input.Keys.UP, false);
        key(Input.Keys.DOWN, false);
        key(Input.Keys.LEFT, false);
//            invalidateHierarchy();
//            invalidate();
//            layout();
    }

    @Override
    public float getPrefWidth() {
        // force dialog width
        return 800f;
    }

    @Override
    public float getPrefHeight() {
        // force dialog height
        return 400f;
    }

    @Override
    public void result(final Object object) {
        seeWares = Boolean.parseBoolean(object.toString());
        System.out.println(seeWares);
    }

    public void setText(final String text) {
        this.text(text);
    }

    public boolean isSeeWares() {
        return seeWares;
    }
}
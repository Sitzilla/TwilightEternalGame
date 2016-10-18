package com.evansitzes.game.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by evan on 9/22/16.
 */
public class Conversation extends Dialog {

    public Conversation() {
        super("", new Skin(Gdx.files.internal("skins/golden-ui-skin.json")));
//        skin = new Skin(Gdx.files.internal("skins/golden-ui-skin.json"));
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
        button("yes", true);

        key(Input.Keys.SPACE, true);
        key(Input.Keys.ENTER, true);
        key(Input.Keys.RIGHT, true);
        key(Input.Keys.UP, true);
        key(Input.Keys.DOWN, true);
        key(Input.Keys.LEFT, true);
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
//            setGameState(State.RUN);
        System.out.println(object);
    }

    public void setText(final String text) {
        this.text(text);
    }

}
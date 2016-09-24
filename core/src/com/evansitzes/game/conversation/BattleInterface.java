package com.evansitzes.game.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

/**
 * Created by evan on 9/23/16.
 */
public class BattleInterface extends Dialog {

    private List<String> choices;

    public BattleInterface() {
        super("", new Skin(Gdx.files.internal("skins/golden-ui-skin.json")));

        final Skin skin = new Skin(Gdx.files.internal("skins/golden-ui-skin.json"));
        this.setMovable(false);
        setResizable(false);
//            Label label = new Label("", skin);
//            label.setWrap(true);
//            label.setFontScale(.8f);
//            label.setAlignment(Align.center);

//        padTop(50).padBottom(50);
//            getContentTable().add(label).width(250).row();

//        getButtonTable().padTop(50);
        setPosition(600, 0);

//        stage.addActor(this);

//            TextButton dbutton = new TextButton("Yes", skin);
        button("yes", true);

//        key(Input.Keys.ENTER, true);
//            invalidateHierarchy();
//            invalidate();
//            layout();

        choices = new List<String>(new Skin(Gdx.files.internal("skins/golden-ui-skin.json")));

        Array choiceItems = new Array();
        choiceItems.add("Attack");
        choiceItems.add("Run");
        choiceItems.add("Pee Pants");
        choices.setItems(choiceItems);

        ScrollPane scrollPane = new ScrollPane(choices);
        scrollPane.setOverscroll(false, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setForceScroll(true, false);
        scrollPane.setScrollBarPositions(false, true);

        this.defaults().expand().fill();
//        battleInterface.row();
        this.add(scrollPane).pad(10, 10, 10, 10);
        this.pack();

        choices.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final String choice = choices.getSelected();

                System.out.println("You are: " + choice);
            }
        });
    }

    @Override
    public float getPrefWidth() {
        // force dialog width
        return 650f;
    }

    @Override
    public float getPrefHeight() {
        // force dialog height
        return 200f;
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

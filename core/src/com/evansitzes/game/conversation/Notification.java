package com.evansitzes.game.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Notification extends Dialog {

    static Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
    private Label label;

    public Notification(final String text) {
        super("", skin);
        setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 10);
        setPosition(0, Gdx.graphics.getHeight() + getHeight());
        label = new Label(text, skin);
        add(label).center();

        label.setColor(Color.WHITE);
        setKeepWithinStage(false);
    }

    public void setLabel(final String text) {
        label.setText(text);
    }

    public void show(){
        final MoveToAction moveTo = new MoveToAction();
        moveTo.setPosition(getX(), Gdx.graphics.getHeight() - getHeight());
        moveTo.setDuration(0.8f);

        final MoveToAction moveBack = new MoveToAction();
        moveBack.setPosition(getX(), Gdx.graphics.getHeight() + getHeight());
        moveBack.setDuration(0.8f);


        final SequenceAction action = new SequenceAction(moveTo, Actions.delay(0.8f), moveBack, Actions.removeActor());
        addAction(action);
    }

}

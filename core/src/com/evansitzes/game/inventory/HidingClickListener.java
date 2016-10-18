package com.evansitzes.game.inventory;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by evan on 9/27/16.
 */
public class HidingClickListener extends ClickListener {

    private Actor actor;

    public HidingClickListener(final Actor actor) {
        this.actor = actor;
    }

    @Override
    public void clicked(final InputEvent event, final float x, final float y) {
        actor.setVisible(false);
    }

}
package com.evansitzes.game.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by evan on 9/24/16.
 */
public class BattleStatus extends Dialog {

    public BattleStatus() {
//        super("Battle Status", new Skin(Gdx.files.internal("skins/golden-ui-skin.json")));
        super("Battle Status", new Skin(Gdx.files.internal("skins/james/plain-james-ui.json")));
//        final Skin skin = new Skin(Gdx.files.internal("skins/golden-ui-skin.json"));
        this.setMovable(false);
        setResizable(false);
        setPosition(50, 0);
        this.pack();


    }

    @Override
    public float getPrefWidth() {
        // force dialog width
        return 500f;
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
}

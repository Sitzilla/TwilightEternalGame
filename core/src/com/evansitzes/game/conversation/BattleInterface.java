package com.evansitzes.game.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.entity.team.Player;
import com.evansitzes.game.helpers.BattleChoiceEnum;

/**
 * Created by evan on 9/23/16.
 */
public class BattleInterface extends Dialog {

    private List<String> choices;
    private BattleChoiceEnum currentChoice;
//    private BattleStatusEnum status;

    public BattleInterface(final Array<Enemy> enemies, final Player player) {
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
//        button("yes", true);

//        key(Input.Keys.ENTER, true);
//            invalidateHierarchy();
//            invalidate();
//            layout();

        choices = new List<String>(new Skin(Gdx.files.internal("skins/golden-ui-skin.json")));
        currentChoice = BattleChoiceEnum.WAITING;
//        status = BattleStatusEnum.ONGOING;

        final Array choiceItems = new Array();
        choiceItems.add("Attack");
        choiceItems.add("Run");
        choiceItems.add("Pee Pants");
        choices.setItems(choiceItems);

        final ScrollPane scrollPane = new ScrollPane(choices);
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
            public void clicked(final InputEvent event, final float x, final float y) {
                final String choice = choices.getSelected();

                if (choice.equals("Attack")) {
                    currentChoice = BattleChoiceEnum.ATTACK;
                } else if (choice.equals("Run")) {
                    currentChoice = BattleChoiceEnum.RUN;

                } else if (choice.equals("Pee Pants")) {
                    currentChoice = BattleChoiceEnum.PEE_PANTS;
                }

//                battleStatus.text("You have: " + currentChoice);
                System.out.println("You are: " + currentChoice);

//                if (currentChoice.equals("Attack")) {
//                    enemies.get(0).takeDamage(player.damage);
//                    if (enemies.get(0).dead == true) {
////                        battleStatus.text("You have killed the monster!");
//                        status = BattleStatusEnum.FINISHED;
//                    }
//                }
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
        System.out.println(currentChoice);
    }

    public void setText(final String text) {
        this.text(text);
    }

//    public BattleStatusEnum pollStatus() {
//        return status;
//    }

    public BattleChoiceEnum pollChoice() {
        return currentChoice;
    }

    public void resetChoice() {
        currentChoice = BattleChoiceEnum.WAITING;
    }
}

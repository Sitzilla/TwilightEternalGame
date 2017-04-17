package com.evansitzes.game.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    public Stage stage;

    public BattleInterface(final Array<Enemy> enemies, final Player player) {
        super("", new Skin(Gdx.files.internal("skins/james/plain-james-ui.json")));
        final Skin skin = new Skin(Gdx.files.internal("skins/james/plain-james-ui.json"));
        this.setMovable(false);
        setResizable(false);
        stage = new Stage();
//            Label label = new Label("", skin);
//            label.setWrap(true);
//            label.setFontScale(.8f);
//            label.setAlignment(Align.center);

//        padTop(50).padBottom(50);
//            getContentTable().add(label).WIDTH(250).row();
//        getButtonTable().padTop(50);
        setPosition(600, 0);

        stage.addActor(this);

//            TextButton dbutton = new TextButton("Yes", skin);
//        button("yes", true);

//        key(Input.Keys.ENTER, true);
//            invalidateHierarchy();
//            invalidate();
//            layout();
        currentChoice = BattleChoiceEnum.WAITING;
        choices = new List<String>(skin);
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
        choices.setSelectedIndex(0);
        stage.addActor(scrollPane);

//        choices.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                clickEvent();
//            }
//            @Override
//            public boolean keyDown (InputEvent event, int keycode) {
//                if (keycode == Input.Keys.ENTER) {
//                    clickEvent();
//                }
//                return true;
//            }
//
//            public void clickEvent() {
//                final String choice = choices.getSelected();
////                disableInterface();
////
//                if (choice.equals("Attack")) {
//                    currentChoice = BattleChoiceEnum.ATTACK;
//                } else if (choice.equals("Run")) {
//                    currentChoice = BattleChoiceEnum.RUN;
//
//                } else if (choice.equals("Pee Pants")) {
//                    currentChoice = BattleChoiceEnum.PEE_PANTS;
//                }
//
////                battleStatus.text("You have: " + currentChoice);
//                System.out.println("You are: " + currentChoice);
//
////                if (currentChoice.equals("Attack")) {
////                    enemies.get(0).takeDamage(player.damage);
////                    if (enemies.get(0).dead == true) {
//////                        battleStatus.text("You have killed the monster!");
////                        status = BattleStatusEnum.FINISHED;
////                    }
////                }
//            }
//
//        });
        this.defaults().expand().fill();
//        battleInterface.row();
        this.add(scrollPane).pad(10, 10, 10, 10);
        this.pack();

        stage.addListener(new InputListener() {
            int buttonToggleState = 1;
            public boolean keyDown (InputEvent event, int keycode) {
                if (keycode == Input.Keys.DOWN) {
                    if (buttonToggleState == 3) {
                        buttonToggleState = 1;
                    } else {
                        buttonToggleState++;
                    }
                    System.out.println("State: " + buttonToggleState);
                }

                if (keycode == Input.Keys.UP) {
                    if (buttonToggleState == 1) {
                        buttonToggleState = 3;
                    } else {
                        buttonToggleState--;
                    }
                    System.out.println("State: " + buttonToggleState);
                }

                switch (buttonToggleState) {
                    case 1:
                        choices.setSelectedIndex(0);
//                        stage.setKeyboardFocus(choices);
                        break;
                    case 2:
                        choices.setSelectedIndex(1);
//                        stage.setKeyboardFocus(choices);
                        break;
                    case 3:
                        choices.setSelectedIndex(2);
//                        stage.setKeyboardFocus(choices);
                        break;
                    default:
                        break;
                }

                if (keycode == Input.Keys.ENTER) {
                    disableInterface();

                    if (buttonToggleState == 1) {
                        currentChoice = BattleChoiceEnum.ATTACK;
                    } else if (buttonToggleState == 2) {
                        currentChoice = BattleChoiceEnum.RUN;

                    } else if (buttonToggleState == 3) {
                        currentChoice = BattleChoiceEnum.PEE_PANTS;
                    }

                    System.out.println("You are: " + currentChoice);
                }

                return true;
            }
        });


//        choices.addListener(new ClickListener() {
//            @Override
//            public void clicked(final InputEvent event, final float x, final float y) {
//                final String choice = choices.getSelected();
//                disableInterface();
//
//                if (choice.equals("Attack")) {
//                    currentChoice = BattleChoiceEnum.ATTACK;
//                } else if (choice.equals("Run")) {
//                    currentChoice = BattleChoiceEnum.RUN;
//
//                } else if (choice.equals("Pee Pants")) {
//                    currentChoice = BattleChoiceEnum.PEE_PANTS;
//                }
//
////                battleStatus.text("You have: " + currentChoice);
//                System.out.println("You are: " + currentChoice);
//
////                if (currentChoice.equals("Attack")) {
////                    enemies.get(0).takeDamage(player.damage);
////                    if (enemies.get(0).dead == true) {
//////                        battleStatus.text("You have killed the monster!");
////                        status = BattleStatusEnum.FINISHED;
////                    }
////                }
//            }
//        });
//
//        stage.addListener(new InputListener() {
//            public boolean keyDown (InputEvent event, int keycode) {
//                System.out.println("down");
//                return true;
//            }
//        });
    }

    @Override
    public float getPrefWidth() {
        // force dialog WIDTH
        return 650f;
    }

    @Override
    public float getPrefHeight() {
        // force dialog HEIGHT
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

    public void disableInterface() {
        this.setTouchable(Touchable.disabled);
    }

    public void enableInterface() {
        this.setTouchable(Touchable.enabled);
    }

    public BattleChoiceEnum pollChoice() {
        return currentChoice;
    }

    public void resetChoice() {
        currentChoice = BattleChoiceEnum.WAITING;
    }

}

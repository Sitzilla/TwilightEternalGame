package com.evansitzes.game.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.evansitzes.game.entity.enemy.Enemy;
import com.evansitzes.game.entity.team.Player;
/**
 * Created by evan on 9/23/16.
 */
public class BattleInterface extends Dialog {

    private List<String> choices;
    private BattleChoiceEnum currentChoice;
    private BattleChoiceEnum currentHover;
//    private BattleStatusEnum status;
    public Stage stage;
    private int currentChoiceIndex;
    private boolean choiceSelected;
    private boolean canMove;
    private ScrollPane scrollPane;
    final Skin skin;

    public BattleInterface(final Array<Enemy> enemies, final Player player) {
        super("", new Skin(Gdx.files.internal("skins/james/plain-james-ui.json")));
        skin = new Skin(Gdx.files.internal("skins/james/plain-james-ui.json"));
        this.setMovable(false);
        setResizable(false);
        stage = new Stage();
        setPosition(600, 0);
        stage.addActor(this);
        currentChoice = BattleChoiceEnum.WAITING;

    }

    public void setInterface(final java.util.List<BattleInterfaceSelection> battleInterfaceSelections) {
        final Array choiceItems = new Array();
        choices = new List<String>(skin);
        this.clear();
        stage.clear();
        choiceSelected = false;

        for (final BattleInterfaceSelection option : battleInterfaceSelections) {
            choiceItems.add(option.name);
        }

        choices.setItems(choiceItems);
        choices.setSelectedIndex(0);

        scrollPane = new ScrollPane(choices);
        scrollPane.setOverscroll(false, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setForceScroll(true, false);
        scrollPane.setScrollBarPositions(false, true);
        scrollPane.setPosition(400, 0);
        stage.addActor(scrollPane);

        currentHover = battleInterfaceSelections.get(0).choice;
        currentChoiceIndex = 0;

        this.defaults().expand().fill();
        this.add(scrollPane).pad(10, 10, 10, 10);
        this.pack();

        stage.addListener(new InputListener() {
            int buttonToggleState = 0;
            public boolean keyDown (final InputEvent event, final int keycode) {
                if (keycode == Input.Keys.DOWN && canMove) {
                    if (buttonToggleState == battleInterfaceSelections.size() - 1) {
                        buttonToggleState = 0;
                        currentChoiceIndex = buttonToggleState;
                    } else {
                        buttonToggleState++;
                        currentChoiceIndex = buttonToggleState;
                    }
                    System.out.println("State: " + buttonToggleState);
                    currentHover = battleInterfaceSelections.get(buttonToggleState).choice;
                }

                if (keycode == Input.Keys.UP && canMove) {
                    if (buttonToggleState == 0) {
                        buttonToggleState = battleInterfaceSelections.size() - 1;
                        currentChoiceIndex = buttonToggleState;
                    } else {
                        buttonToggleState--;
                        currentChoiceIndex = buttonToggleState;
                    }
                    System.out.println("State: " + buttonToggleState);
                    currentHover = battleInterfaceSelections.get(buttonToggleState).choice;
                }

                if (canMove) {
                    choices.setSelectedIndex(buttonToggleState);
                }

                if (keycode == Input.Keys.ENTER && canMove) {
                    currentChoiceIndex = buttonToggleState;
                    choiceSelected = true;
                    currentChoice = battleInterfaceSelections.get(buttonToggleState).choice;
                    System.out.println("You are: " + currentChoice);
                }

                return true;
            }
        });
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

    public void disableInterface() {
        scrollPane.setVisible(false);
        canMove = false;
    }

    public void enableInterface() {
        scrollPane.setVisible(true);
        canMove = true;
    }

    public BattleChoiceEnum pollHover() {
        return currentHover;
    }

    public BattleChoiceEnum pollChoice() {
        return currentChoice;
    }

    public void resetChoice() {
        choiceSelected = false;
        currentChoice = BattleChoiceEnum.WAITING;
    }

    public boolean isChoiceSelected() {
        return choiceSelected;
    }

    public int getCurrentChoiceIndex() {
        return currentChoiceIndex;
    }
}

package com.evansitzes.game.inventory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by evan on 9/27/16.
 */
public class TooltipListener extends InputListener {

    private boolean inside;

    private Actor tooltip;
    private boolean followCursor;

    private Vector2 position = new Vector2();
    private Vector2 tmp = new Vector2();
    private Vector2 offset = new Vector2(10, 10);

    public TooltipListener(final Actor tooltip, final boolean followCursor) {
        this.tooltip = tooltip;
        this.followCursor = followCursor;
    }

    @Override
    public boolean mouseMoved(final InputEvent event, final float x, final float y) {
        if (inside && followCursor) {
            event.getListenerActor().localToStageCoordinates(tmp.set(x, y));
            tooltip.setPosition(tmp.x + position.x + offset.x, tmp.y + position.y + offset.y);
        }
        return false;
    }

    @Override
    public void enter(final InputEvent event, final float x, final float y, final int pointer, final Actor fromActor) {
        inside = true;
        tooltip.setVisible(true);
        tmp.set(x, y);
        event.getListenerActor().localToStageCoordinates(tmp);
        tooltip.setPosition(tmp.x + position.x + offset.x, tmp.y + position.y + offset.y);
        tooltip.toFront();
    }

    @Override
    public void exit(final InputEvent event, final float x, final float y, final int pointer, final Actor toActor) {
        inside = false;
        tooltip.setVisible(false);
    }

}
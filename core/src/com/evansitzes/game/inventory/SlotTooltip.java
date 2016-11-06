package com.evansitzes.game.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * Created by evan on 9/27/16.
 */
public class SlotTooltip extends Window implements SlotListener {

    private Skin skin;

    private Slot slot;

    public SlotTooltip(final Slot slot, final Skin skin) {
        super("Article", skin);
        this.slot = slot;
        this.skin = skin;
        hasChanged(slot);
        slot.addListener(this);
        setVisible(false);
    }

    @Override
    public void hasChanged(final Slot slot) {
        if (slot.isEmpty()) {
            setVisible(false);
            return;
        }

        clear();
        final Label label = new Label(slot.getItem().getName() + " \n" + slot.getItem().getDescription(), skin);
        add(label);
        pack();
    }

    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
        // the listener sets this to true in case the slot is hovered
        // however, we don't want that in case the slot is empty
        if (slot.isEmpty()) {
            super.setVisible(false);
        }
    }


}
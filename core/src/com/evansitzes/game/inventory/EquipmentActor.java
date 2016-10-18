package com.evansitzes.game.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

/**
 * Created by evan on 9/27/16.
 */
public class EquipmentActor extends Window {

    public EquipmentActor(final Inventory inventory, final DragAndDrop dragAndDrop, final Skin skin) {
        super("Equipment", skin);

        // basic layout
        setPosition(100, 700);
        defaults().space(8);
        row().fill().expandX();

        // run through all slots and create SlotActors for each
        for (final Slot slot : inventory.getSlots()) {
            final SlotActor slotActor = new SlotActor(skin, slot);
            add(slotActor);

            dragAndDrop.addSource(new SlotSource(slotActor));
            dragAndDrop.addTarget(new SlotTarget(slotActor));

            // every cell we are going to jump to a new row
            row();
        }

        pack();

        // it is hidden by default
        setVisible(false);
    }

}

package com.evansitzes.game.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.evansitzes.game.screens.TwilightEternalScreen;

/**
 * Created by evan on 11/4/16.
 */
public class ShopInventory extends Window {

    public ShopInventory(final TwilightEternalScreen currentScreen, final Inventory inventory, final DragAndDrop dragAndDrop, final Skin skin) {
        super("Shop Inventory", skin);

        // basic layout
        defaults().space(8);
        row().fill().expandX();

        // run through all slots and create SlotActors for each
        int i = 0;
        for (final Slot slot : inventory.getSlots()) {
            final SlotActor slotActor = new SlotActor(currentScreen, skin, slot);
            add(slotActor);

            dragAndDrop.addSource(new SlotSource(slotActor));
            dragAndDrop.addTarget(new SlotTarget(slotActor));

            i++;
            // every 5 cells, we are going to jump to a new row
            if (i % 5 == 0) {
                row();
            }
        }

        pack();

        // it is hidden by default
        setVisible(false);
    }
}

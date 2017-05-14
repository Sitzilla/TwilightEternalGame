package com.evansitzes.game.inventory;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.evansitzes.game.screens.TwilightEternalScreen;

/**
 * Created by evan on 9/27/16.
 */
public class InventoryActor extends Window {


    public InventoryActor(final TwilightEternalScreen currentScreen, final CurrentInventory inventory, final DragAndDrop dragAndDrop, final Skin skin) {
        super("CurrentInventory", skin);

        // basic layout
        defaults().space(8);
        row().fill().expandX();
        this.setMovable(false);

        // run through all slots and create SlotActors for each
        int i = 0;
        for (final Slot slot : inventory.getSlots()) {
            final SlotActor slotActor = new SlotActor(currentScreen, skin, slot);
            add(slotActor);

            slotActor.addListener(new ClickListener() {
                @Override
                public void clicked(final InputEvent event, final float x, final float y) {

                    if (getTapCount() == 2) {
                        currentScreen.doubleClickItem(slotActor);
                    }
                }


            });

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

//    @Override
//    public void hasChanged(final Slot slot) {
//        // when the slot changes, we switch the icon via a new style
//        System.out.println("Parent: " + getParent().getName());
//    }
}

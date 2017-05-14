package com.evansitzes.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.evansitzes.game.inventory.Slot;
import com.evansitzes.game.inventory.SlotActor;

/**
 * Created by evan on 10/24/16.
 */
public abstract class TwilightEternalScreen implements Screen {
    public Stage stage;

    public void wasClicked(final SlotActor slotActor) {

    }

    public void doubleClickItem(final SlotActor slotActor) {
    }

    public void switchItems(final Slot item, final Slot payloadSlotItem) {

    }

    public void setInventoryScreen() {

    }

    public void setConversationWindow(final String text) {

    }
}
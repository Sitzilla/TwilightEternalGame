package com.evansitzes.game.inventory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.helpers.Textures.Items;
import com.evansitzes.game.screens.TwilightEternalScreen;

/**
 * Created by evan on 9/27/16.
 */
public class SlotActor extends ImageTextButton implements SlotListener {

    private TwilightEternalScreen currentScreen;
    private Slot slot;
    private Skin skin;

    public SlotActor(final TwilightEternalScreen currentScreen, final Skin skin, final Slot slot) {
        super("", createStyle(skin, slot));
        this.slot = slot;
        this.skin = skin;
        this.currentScreen = currentScreen;

        if (slot.getAmount() > 0 && slot.getItem().isCombinable) {
            this.setText(slot.getAmount() + "x");
        }

        // this actor has to be notified when the slot itself changes
        slot.addListener(this);

        final SlotTooltip tooltip = new SlotTooltip(slot, skin);
        currentScreen.stage.addActor(tooltip);
        addListener(new TooltipListener(tooltip, true));
    }

    /**
     * This will create a new style for our image button, with the correct image for the item type.
     */
    private static ImageTextButtonStyle createStyle(final Skin skin, final Slot slot) {
        final TextureRegion image;
        if (slot.getItem() != null) {
            image = slot.getItem().texture;
        } else {
            image = Items.BLANK;
        }

        final ImageTextButtonStyle style = new ImageTextButtonStyle(skin.get(TextButtonStyle.class));
        style.imageUp = new TextureRegionDrawable(image);
        style.imageDown = new TextureRegionDrawable(image);

        return style;
    }

    @Override
    public void hasChanged(final Slot slot) {
        // when the slot changes, we switch the icon via a new style
        if (slot.getAmount() == 0 || !slot.getItem().isCombinable) {
            this.setText("");
        } else {
            this.setText(slot.getAmount() + "x");
        }

        setStyle(createStyle(skin, slot));
    }

    public Slot getSlot() {
        return slot;
    }

    public TwilightEternalScreen getCurrentScreen() {
        return currentScreen;
    }
}

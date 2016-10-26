package com.evansitzes.game.inventory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.resources.Textures;
import com.evansitzes.game.screens.TwilightEternalScreen;

/**
 * Created by evan on 9/27/16.
 */
public class SlotActor extends ImageButton implements SlotListener {

    private Slot slot;

    private Skin skin;

    public SlotActor(final TwilightEternalScreen currentScreen, final Skin skin, final Slot slot) {
        super(createStyle(skin, slot));
        this.slot = slot;
        this.skin = skin;

        // this actor has to be notified when the slot itself changes
        slot.addListener(this);

        final SlotTooltip tooltip = new SlotTooltip(slot, skin);
        currentScreen.stage.addActor(tooltip);
        addListener(new TooltipListener(tooltip, true));
    }

    /**
     * This will create a new style for our image button, with the correct image for the item type.
     */
    private static ImageButtonStyle createStyle(final Skin skin, final Slot slot) {
        final TextureRegion image;
        if (slot.getItem() != null) {
            image = slot.getItem().getTextureRegion();
        } else {
            image = Textures.Items.BLANK;
        }
        final ImageButtonStyle style = new ImageButtonStyle(skin.get(ButtonStyle.class));
        style.imageUp = new TextureRegionDrawable(image);
        style.imageDown = new TextureRegionDrawable(image);

        return style;
    }

    @Override
    public void hasChanged(final Slot slot) {
        // when the slot changes, we switch the icon via a new style
        setStyle(createStyle(skin, slot));
    }

    public Slot getSlot() {
        return slot;
    }

}

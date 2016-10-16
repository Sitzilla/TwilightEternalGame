package com.evansitzes.game.inventory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.screens.InventoryScreen;

/**
 * Created by evan on 9/27/16.
 */
public class SlotActor extends ImageButton implements SlotListener {

    private Slot slot;

    private Skin skin;

    public SlotActor(Skin skin, Slot slot) {
        super(createStyle(skin, slot));
        this.slot = slot;
        this.skin = skin;

        // this actor has to be notified when the slot itself changes
        slot.addListener(this);

        SlotTooltip tooltip = new SlotTooltip(slot, skin);
        InventoryScreen.stage.addActor(tooltip);
        addListener(new TooltipListener(tooltip, true));
    }

    /**
     * This will create a new style for our image button, with the correct image for the item type.
     */
    private static ImageButtonStyle createStyle(Skin skin, Slot slot) {
//        TextureAtlas icons = new TextureAtlas(Gdx.files.internal("icons/icons.atlas"));
        TextureRegion image;
//        if (slot.getItem() != null) {
//            image = icons.findRegion(slot.getItem().getTextureRegion());
            image = slot.getItem().getTextureRegion();
//        } else {
//            // we have a special "empty" region in our atlas file, which is just black
////            image = icons.findRegion("nothing");
//        }
        ImageButtonStyle style = new ImageButtonStyle(skin.get(ButtonStyle.class));
        style.imageUp = new TextureRegionDrawable(image);
        style.imageDown = new TextureRegionDrawable(image);

        return style;
    }

    @Override
    public void hasChanged(Slot slot) {
        // when the slot changes, we switch the icon via a new style
        setStyle(createStyle(skin, slot));
    }

    public Slot getSlot() {
        return slot;
    }

}

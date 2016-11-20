package com.evansitzes.game.icons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evansitzes.game.helpers.Textures;
import com.evansitzes.game.inventory.Slot;
import com.evansitzes.game.inventory.SlotListener;

/**
 * Created by evan on 11/19/16.
 */
public class Icon extends ImageButton implements SlotListener {

    private String name;

    public Icon(final Skin skin, final String name) {
        super(createStyle(skin, name));
        this.name = name;
    }

    @Override
    public void hasChanged(final Slot slot) {

    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * This will create a new style for our image button, with the correct image for the item type.
     */
    private static ImageButtonStyle createStyle(final Skin skin, final String name) {
        final TextureRegion image;

        if (name.equals("backpack")) {
            image = Textures.Items.BACKPACK;
        } else {
            image = Textures.Items.SPELLS;
        }

        final ImageButtonStyle style = new ImageButtonStyle(skin.get(ButtonStyle.class));
        style.imageUp = new TextureRegionDrawable(image);
        style.imageDown = new TextureRegionDrawable(image);

        return style;
    }
}

package com.evansitzes.game.inventory;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evansitzes.game.helpers.Textures.Items;

/**
 * Created by evan on 9/27/16.
 */
public class Item {
    public TextureRegion texture;
    public InventoryTypeEnum inventoryType;
    public String name;
    public String description;
    public boolean isCombinable;
    public Sprite sprite;

    public Item(final String name, final InventoryTypeEnum inventoryType, final String description, final boolean isCombinable) {
        this.name = name;
        this.inventoryType = inventoryType;
        this.description = description;
        this.texture = new TextureRegion(parseTexture(name));
        this.isCombinable = isCombinable;
        this.sprite = new Sprite(texture);

    }

    //TODO replace with some kind of lookup
    private TextureRegion parseTexture(final String name) {
        if (name.equals("bronze_helmet")) {
            return Items.BRONZE_HELMET;
        }

        if (name.equals("bronze_armor")) {
            return Items.BRONZE_ARMOR;
        }

        if (name.equals("bronze_pants")) {
            return Items.BRONZE_PANTS;
        }

        if (name.equals("bronze_boots")) {
            return Items.BRONZE_BOOTS;
        }

        if (name.equals("bronze_sword")) {
            return Items.BRONZE_SWORD;
        }

        if (name.equals("iron_helmet")) {
            return Items.IRON_HELMET;
        }

        if (name.equals("iron_armor")) {
            return Items.IRON_ARMOR;
        }

        if (name.equals("iron_pants")) {
            return Items.IRON_PANTS;
        }

        if (name.equals("iron_boots")) {
            return Items.IRON_BOOTS;
        }

        if (name.equals("iron_sword")) {
            return Items.IRON_SWORD;
        }

        if (name.equals("Apple")) {
            return Items.APPLE;
        }

        if (name.equals("Bone")) {
            return Items.BONE;
        }

        if (name.equals("Veggies")) {
            return Items.VEGGIES;
        }

        return Items.BLANK;
    }
}

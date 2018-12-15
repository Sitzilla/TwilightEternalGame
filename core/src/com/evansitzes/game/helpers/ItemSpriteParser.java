package com.evansitzes.game.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evansitzes.game.helpers.Textures.Items;

public class ItemSpriteParser {

    public static TextureRegion parse(final String name) {
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

        if (name.equals("red-potion")) {
            return Items.RED_POTION;
        }

        if (name.equals("blue-potion")) {
            return Items.BLUE_POTION;
        }

        return Items.BLANK;
    }
}
package com.evansitzes.game.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemSpriteParser {

    public static TextureRegion parse(final String name) {
        if (name.equals("bronze_helmet")) {
            return Textures.Items.BRONZE_HELMET;
        }

        if (name.equals("bronze_armor")) {
            return Textures.Items.BRONZE_ARMOR;
        }

        if (name.equals("bronze_pants")) {
            return Textures.Items.BRONZE_PANTS;
        }

        if (name.equals("bronze_boots")) {
            return Textures.Items.BRONZE_BOOTS;
        }

        if (name.equals("bronze_sword")) {
            return Textures.Items.BRONZE_SWORD;
        }

        if (name.equals("iron_helmet")) {
            return Textures.Items.IRON_HELMET;
        }

        if (name.equals("iron_armor")) {
            return Textures.Items.IRON_ARMOR;
        }

        if (name.equals("iron_pants")) {
            return Textures.Items.IRON_PANTS;
        }

        if (name.equals("iron_boots")) {
            return Textures.Items.IRON_BOOTS;
        }

        if (name.equals("iron_sword")) {
            return Textures.Items.IRON_SWORD;
        }

        if (name.equals("Apple")) {
            return Textures.Items.APPLE;
        }

        if (name.equals("Bone")) {
            return Textures.Items.BONE;
        }

        if (name.equals("Veggies")) {
            return Textures.Items.VEGGIES;
        }

        if (name.equals("red-potion")) {
            return Textures.Items.RED_POTION;
        }

        return Textures.Items.BLANK;
    }
}
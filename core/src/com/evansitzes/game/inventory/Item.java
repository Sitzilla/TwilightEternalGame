package com.evansitzes.game.inventory;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evansitzes.game.helpers.Textures;

/**
 * Created by evan on 9/27/16.
 */
public class Item {
    private TextureRegion texture;
    private String name;
    private String description;
    public Sprite sprite;

    public Item(final String name, final String description) {
        this.name = name;
        this.description = description;
        this.texture = new TextureRegion(getTexture(name));
        this.sprite = new Sprite(texture);

    }

    public TextureRegion getTextureRegion() {
        return texture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    //TODO replace this with DB lookup
    private TextureRegion getTexture(final String name) {
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

        if (name.equals("apple")) {
            return Textures.Items.APPLE;
        }

        if (name.equals("bone")) {
            return Textures.Items.BONE;
        }

        if (name.equals("veggies")) {
            return Textures.Items.VEGGIES;
        }

        return Textures.Items.BLANK;
    }
}

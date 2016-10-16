package com.evansitzes.game.inventory;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evansitzes.game.resources.Textures;

/**
 * Created by evan on 9/27/16.
 */
public class Item {
    private TextureRegion texture;
    public final String name;
    public final Sprite sprite;


    Item(final String name) {
        this.name = name;
        this.texture = new TextureRegion(Textures.Items.BRONZE_ARMOR);
        this.sprite = new Sprite(texture);

    }

    public TextureRegion getTextureRegion() {
        return texture;
    }
}

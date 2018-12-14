package com.evansitzes.game.inventory;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evansitzes.game.helpers.ItemSpriteParser;
import com.evansitzes.game.helpers.ItemTypeParser;
import com.evansitzes.game.model.Article;

import java.util.HashMap;

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
    public HashMap<String, Integer> consumable;

    public Item(final Article article) {
        this.name = article.getName();
        this.inventoryType = ItemTypeParser.parse(article);
        this.description = article.getDescription();
        this.texture = new TextureRegion(ItemSpriteParser.parse(name));
        this.isCombinable = article.isCombinable();
        this.consumable = article.getConsumable();
        this.sprite = new Sprite(texture);

    }
}

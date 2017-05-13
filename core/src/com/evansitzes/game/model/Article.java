package com.evansitzes.game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

/**
 * Created by evan on 11/4/16.
 */
public class Article {
    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty("item_type")
    private String itemType;

    @JsonProperty
    private HashMap<String, Integer> attributes;

    public Article() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public HashMap getAttributes() {
        return attributes;
    }

    public void setAttributes(final HashMap<String, Integer> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Article{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", itemType='" + itemType + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}

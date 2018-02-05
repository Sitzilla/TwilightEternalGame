package com.evansitzes.game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class NpcConfiguration {
    @JsonProperty
    private String tag;

    @JsonProperty
    private String text;

    @JsonProperty
    private String sprite;

    @JsonProperty
    private ArrayList<String> inventory;

    public String getTag() {
        return tag;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(final String sprite) {
        this.sprite = sprite;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public void setInventory(final ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "NpcConfiguration{" +
                "tag='" + tag + '\'' +
                ", text='" + text + '\'' +
                ", sprite='" + sprite + '\'' +
                ", inventory=" + inventory +
                '}';
    }
}

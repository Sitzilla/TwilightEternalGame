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
    private ArrayList<String> default_inventory;

    @JsonProperty
    private ArrayList<String> current_inventory;

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

    public ArrayList<String> getDefault_inventory() {
        return default_inventory;
    }

    public void setDefault_inventory(ArrayList<String> default_inventory) {
        this.default_inventory = default_inventory;
    }

    public ArrayList<String> getCurrent_inventory() {
        return current_inventory;
    }

    public void setCurrent_inventory(final ArrayList<String> current_inventory) {
        this.current_inventory = current_inventory;
    }

    @Override
    public String toString() {
        return "NpcConfiguration{" +
                "tag='" + tag + '\'' +
                ", text='" + text + '\'' +
                ", sprite='" + sprite + '\'' +
                ", default_inventory=" + default_inventory +
                ", current_inventory=" + current_inventory +
                '}';
    }
}

package com.evansitzes.game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by evan on 11/4/16.
 */
public class Article {
    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    public Article() {
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

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Article{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

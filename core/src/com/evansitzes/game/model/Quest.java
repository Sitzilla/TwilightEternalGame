package com.evansitzes.game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by evan on 11/4/16.
 */
public class Quest {
    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private String title;

    @JsonProperty
    private boolean completed;

    public Quest() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(final boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}

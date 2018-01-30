package com.evansitzes.game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Price {
    @JsonProperty
    private String name;

    @JsonProperty
    private int cost;

    public Price() {

    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(final int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Price{" +
                "name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}

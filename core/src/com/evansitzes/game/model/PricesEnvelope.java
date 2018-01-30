package com.evansitzes.game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;

public class PricesEnvelope {
    @JsonProperty
    private ArrayList<Price> prices;

    public PricesEnvelope() {

    }

    public ArrayList<Price> getPrices() {
        return prices;
    }

    public void setPrices(final ArrayList<Price> prices) {
        this.prices = prices;
    }

    public HashMap<String, Integer> getMap() {
        final HashMap<String, Integer> pricesMap = new HashMap<String, Integer>();

        for (final Price price : prices) {
            pricesMap.put(price.getName(), price.getCost());
        }

        return pricesMap;
    }

    @Override
    public String toString() {
        return "PricesEnvelope{" +
                "prices=" + prices +
                '}';
    }
}

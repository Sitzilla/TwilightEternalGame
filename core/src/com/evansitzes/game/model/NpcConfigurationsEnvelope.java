package com.evansitzes.game.model;

import com.evansitzes.game.exceptions.ItemDoesntExistException;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class NpcConfigurationsEnvelope {
    @JsonProperty("npc_configurations")
    private ArrayList<NpcConfiguration> configurations;

    public NpcConfigurationsEnvelope() {
    }

    public ArrayList<NpcConfiguration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(final ArrayList<NpcConfiguration> configurations) {
        this.configurations = configurations;
    }

    public NpcConfiguration getConfiguration(final String tag) {

        for (final NpcConfiguration configuration : configurations) {
            if (configuration.getTag().equals(tag)) {
                return configuration;
            }
        }

        throw new ItemDoesntExistException("Npc " + tag + " does not exist in resources file.");
    }

    @Override
    public String toString() {
        return "NpcConfigurationsEnvelope{" +
                "configurations=" + configurations +
                '}';
    }
}
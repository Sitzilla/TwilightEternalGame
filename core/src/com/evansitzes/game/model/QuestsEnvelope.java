package com.evansitzes.game.model;

import com.evansitzes.game.exceptions.ItemDoesntExistException;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by evan on 11/3/16.
 */
public class QuestsEnvelope {
    @JsonProperty
    private ArrayList<Quest> quests;

    public QuestsEnvelope() {
    }

    public ArrayList<Quest> getQuests() {
        return quests;
    }

    public void setQuests(ArrayList<Quest> quests) {
        this.quests = quests;
    }

    public Quest getQuest(final String name) {

        for (final Quest quest : quests) {
            if (quest.getName().equals(name)) {
                return quest;
            }
        }

        throw new ItemDoesntExistException("Quest " + name + " does not exist in resources file.");
    }

    @Override
    public String toString() {
        return "QuestsEnvelope{" +
                "quests=" + quests +
                '}';
    }
}

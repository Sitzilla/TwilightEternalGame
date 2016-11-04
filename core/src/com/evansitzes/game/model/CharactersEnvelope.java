package com.evansitzes.game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by evan on 11/3/16.
 */
public class CharactersEnvelope {
    @JsonProperty
    private ArrayList<Character> characters;

    public CharactersEnvelope() {
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(final ArrayList<Character> characters) {
        this.characters = characters;
    }

    @Override
    public String toString() {
        return "CharactersEnvelope{" +
                "characters=" + characters +
                '}';
    }
}

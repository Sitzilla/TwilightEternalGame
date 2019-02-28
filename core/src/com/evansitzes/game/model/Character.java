package com.evansitzes.game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by evan on 11/3/16.
 */
public class Character {
    @JsonProperty
    private String name;
    @JsonProperty
    private int gold;
//    @JsonProperty("deployment_date")
//    private DateTime deploymentDate;
    @JsonProperty
    private Map<String, String> equipment;
    @JsonProperty
    private ArrayList<String> inventory;
    @JsonProperty
    private ArrayList<String> quests;
//    @JsonProperty("datasource_ids")
//    private ArrayList<String> datasourceIds;
    @JsonProperty("base_attributes")
    private Map<String, Integer> baseAttributes;
    @JsonProperty
    private Integer experience;
    @JsonProperty
    private Integer level;
    @JsonProperty("current_hit_points")
    private Float currentHitPoints;
    @JsonProperty("current_magic_points")
    private Float currentMagicPoints;

    public Character() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Map<String, String> getEquipment() {
        return equipment;
    }

    public void setEquipment(final Map<String, String> equipment) {
        this.equipment = equipment;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public void setInventory(final ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(final int gold) {
        this.gold = gold;
    }

    public Map<String, Integer> getBaseAttributes() {
        return baseAttributes;
    }

    public void setBaseAttributes(final Map<String, Integer> baseAttributes) {
        this.baseAttributes = baseAttributes;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(final Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(final Integer level) {
        this.level = level;
    }

    public Float getCurrentHitPoints() {
        return currentHitPoints;
    }

    public void setCurrentHitPoints(final Float currentHitPoints) {
        this.currentHitPoints = currentHitPoints;
    }

    public Float getCurrentMagicPoints() {
        return currentMagicPoints;
    }

    public void setCurrentMagicPoints(final Float currentMagicPoints) {
        this.currentMagicPoints = currentMagicPoints;
    }

    public ArrayList<String> getQuests() {
        return quests;
    }

    public void setQuests(ArrayList<String> quests) {
        this.quests = quests;
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", gold=" + gold +
                ", equipment=" + equipment +
                ", inventory=" + inventory +
                ", quests=" + quests +
                ", baseAttributes=" + baseAttributes +
                ", experience=" + experience +
                ", level=" + level +
                ", currentHitPoints=" + currentHitPoints +
                ", currentMagicPoints=" + currentMagicPoints +
                '}';
    }
}

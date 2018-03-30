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
//    @JsonProperty("datasource_ids")
//    private ArrayList<String> datasourceIds;
    @JsonProperty("base_attributes")
    private Map<String, Integer> baseAttributes;
    @JsonProperty("base_armor")
    private Integer baseArmor;
    @JsonProperty
    private Integer experience;
    @JsonProperty
    private Integer level;
    @JsonProperty("current_health")
    private Float currentHealth;
    @JsonProperty("base_health")
    private Float baseHealth;

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

    public Integer getBaseArmor() {
        return baseArmor;
    }

    public void setBaseArmor(final Integer baseArmor) {
        this.baseArmor = baseArmor;
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

    public Float getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(final Float currentHealth) {
        this.currentHealth = currentHealth;
    }

    public Float getBaseHealth() {
        return baseHealth;
    }

    public void setBaseHealth(final Float baseHealth) {
        this.baseHealth = baseHealth;
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", gold=" + gold +
                ", equipment=" + equipment +
                ", inventory=" + inventory +
                ", baseAttributes=" + baseAttributes +
                ", baseArmor=" + baseArmor +
                ", experience=" + experience +
                ", level=" + level +
                ", currentHealth=" + currentHealth +
                ", baseHealth=" + baseHealth +
                '}';
    }
}

package com.evansitzes.game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by evan on 11/3/16.
 */
public class Character {
    @JsonProperty
    private String name;
//    @JsonProperty
//    private String id;
//    @JsonProperty("deployment_date")
//    private DateTime deploymentDate;
    @JsonProperty
    private ArrayList<String> equipment;
    @JsonProperty
    private ArrayList<String> inventory;
//    @JsonProperty("datasource_ids")
//    private ArrayList<String> datasourceIds;

    public Character() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getEquipment() {
        return equipment;
    }

    public void setEquipment(final ArrayList<String> equipment) {
        this.equipment = equipment;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    //    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public DateTime getDeploymentDate() {
//        return deploymentDate;
//    }
//
//    public void setDeploymentDate(DateTime deploymentDate) {
//        this.deploymentDate = deploymentDate;
//    }
//
//    public ArrayList<String> getConversionTypes() {
//        return conversionTypes;
//    }
//
//    public void setConversionTypes(ArrayList<String> conversionTypes) {
//        this.conversionTypes = conversionTypes;
//    }
//
//    public ArrayList<String> getDatasourceIds() {
//        return datasourceIds;
//    }
//
//    public void setDatasourceIds(ArrayList<String> datasourceIds) {
//        this.datasourceIds = datasourceIds;
//    }


    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", equipment=" + equipment +
                ", inventory=" + inventory +
                '}';
    }
}

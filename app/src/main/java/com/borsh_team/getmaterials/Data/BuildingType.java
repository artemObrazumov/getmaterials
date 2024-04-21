package com.borsh_team.getmaterials.Data;

import java.util.ArrayList;

public class BuildingType {
    private String buildingType;
    private String buildingTypeID;
    private ArrayList<CapacityType> capacity;
    private ArrayList<WorkTypeMenu> workTypes;

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public String getBuildingTypeID() {
        return buildingTypeID;
    }

    public void setBuildingTypeID(String buildingTypeID) {
        this.buildingTypeID = buildingTypeID;
    }

    public ArrayList<CapacityType> getCapacity() {
        return capacity;
    }

    public void setCapacity(ArrayList<CapacityType> capacity) {
        this.capacity = capacity;
    }

    public ArrayList<WorkTypeMenu> getWorkTypes() {
        return workTypes;
    }

    public void setWorkTypes(ArrayList<WorkTypeMenu> workTypes) {
        this.workTypes = workTypes;
    }
}

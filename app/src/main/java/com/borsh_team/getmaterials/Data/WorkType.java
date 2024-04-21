package com.borsh_team.getmaterials.Data;

import java.util.ArrayList;

public class WorkType {
    private String workTypeString;
    private String workTypeID;
    private String fireLevel;
    private ArrayList<Resource> resources;

    public String getWorkTypeString() {
        return workTypeString;
    }

    public void setWorkTypeString(String workTypeString) {
        this.workTypeString = workTypeString;
    }

    public String getWorkTypeID() {
        return workTypeID;
    }

    public void setWorkTypeID(String workTypeID) {
        this.workTypeID = workTypeID;
    }

    public String getFireLevel() {
        return fireLevel;
    }

    public void setFireLevel(String fireLevel) {
        this.fireLevel = fireLevel;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return workTypeString;
    }
}

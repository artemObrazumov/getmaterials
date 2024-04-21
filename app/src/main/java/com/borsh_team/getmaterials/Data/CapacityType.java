package com.borsh_team.getmaterials.Data;

public class CapacityType {
    private String capacityString;
    private String capacityIcon;
    private String capacityMark;

    public String getCapacityString() {
        return capacityString;
    }

    public void setCapacityString(String capacityString) {
        this.capacityString = capacityString;
    }

    public String getCapacityIcon() {
        return capacityIcon;
    }

    public void setCapacityIcon(String capacityIcon) {
        this.capacityIcon = capacityIcon;
    }

    public String getCapacityMark() {
        return capacityMark;
    }

    public void setCapacityMark(String capacityMark) {
        this.capacityMark = capacityMark;
    }

    @Override
    public String toString() {
        return capacityString;
    }
}

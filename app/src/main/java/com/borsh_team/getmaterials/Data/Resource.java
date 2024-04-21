package com.borsh_team.getmaterials.Data;

public class Resource {
    private String resourceString;
    private String resourceImage;
    private String resourceInfo;
    private Boolean allMaterialAllowed = false;

    public String getResourceString() {
        return resourceString;
    }

    public void setResourceString(String resourceString) {
        this.resourceString = resourceString;
    }

    public String getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(String resourceImage) {
        this.resourceImage = resourceImage;
    }

    public String getResourceInfo() {
        return resourceInfo;
    }

    public void setResourceInfo(String resourceInfo) {
        this.resourceInfo = resourceInfo;
    }

    public Boolean getAllMaterialAllowed() {
        return allMaterialAllowed;
    }

    public void setAllMaterialAllowed(Boolean allMaterialAllowed) {
        this.allMaterialAllowed = allMaterialAllowed;
    }
}

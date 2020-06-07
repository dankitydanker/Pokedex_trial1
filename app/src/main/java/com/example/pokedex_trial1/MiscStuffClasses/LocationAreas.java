package com.example.pokedex_trial1.MiscStuffClasses;

import com.google.gson.annotations.SerializedName;

public class LocationAreas {
    @SerializedName("name")
    private String areaName;
    @SerializedName("url")
    private String areaURL;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaURL() {
        return areaURL;
    }

    public void setAreaURL(String areaURL) {
        this.areaURL = areaURL;
    }
}

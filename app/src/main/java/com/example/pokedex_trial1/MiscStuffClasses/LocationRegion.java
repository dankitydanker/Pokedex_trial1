package com.example.pokedex_trial1.MiscStuffClasses;

import com.google.gson.annotations.SerializedName;

public class LocationRegion {
    @SerializedName("name")
    private String regionName;
    @SerializedName("url")
    private String regionURL;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionURL() {
        return regionURL;
    }

    public void setRegionURL(String regionURL) {
        this.regionURL = regionURL;
    }
}

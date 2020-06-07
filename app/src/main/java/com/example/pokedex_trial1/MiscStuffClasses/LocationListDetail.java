package com.example.pokedex_trial1.MiscStuffClasses;

import com.google.gson.annotations.SerializedName;

public class LocationListDetail {
    @SerializedName("name")
    private String locationName;
    @SerializedName("url")
    private String locationURL;

    private transient String locationId;

    public String getLocationId() {
        // https://pokeapi.co/api/v2/location/5/
        // 34 is / before id
        return locationURL.substring(35, locationURL.length()-1);
    }

    public void setLocationId(String locationId) {
        this.locationId = locationURL.substring(35, locationURL.length()-1);
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationURL() {
        return locationURL;
    }

    public void setLocationURL(String locationURL) {
        this.locationURL = locationURL;
    }
}

package com.example.pokedex_trial1.MiscStuffClasses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LocationDetail {
    @SerializedName("name")
    private String name;
    @SerializedName("region")
    private LocationRegion region;
    @SerializedName("id")
    private String id;
    @SerializedName("areas")
    private ArrayList<LocationAreas> areas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationRegion getRegion() {
        return region;
    }

    public void setRegion(LocationRegion region) {
        this.region = region;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<LocationAreas> getAreas() {
        return areas;
    }

    public void setAreas(ArrayList<LocationAreas> areas) {
        this.areas = areas;
    }
}

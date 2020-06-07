package com.example.pokedex_trial1.MiscStuffClasses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LocationListData {
    @SerializedName("next")
    private String next;
    @SerializedName("previous")
    private Object previous;
    @SerializedName("results")
    private ArrayList<LocationListDetail> results;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public ArrayList<LocationListDetail> getResults() {
        return results;
    }

    public void setResults(ArrayList<LocationListDetail> results) {
        this.results = results;
    }
}

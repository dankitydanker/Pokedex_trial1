package com.example.pokedex_trial1.MiscStuffClasses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PokeItemDetail {
    @SerializedName("name")
    private String itemName;
    @SerializedName("id")
    private String itemId;
    @SerializedName("effect_entries")
    private ArrayList<EffectEntries> itemEffectEntries;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public ArrayList<EffectEntries> getItemEffectEntries() {
        return itemEffectEntries;
    }

    public void setItemEffectEntries(ArrayList<EffectEntries> itemEffectEntries) {
        this.itemEffectEntries = itemEffectEntries;
    }
}

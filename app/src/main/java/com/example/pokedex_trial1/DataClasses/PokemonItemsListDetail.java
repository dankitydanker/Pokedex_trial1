package com.example.pokedex_trial1.DataClasses;

import com.google.gson.annotations.SerializedName;

public class PokemonItemsListDetail {

    @SerializedName("name")
    private String itemName;
    @SerializedName("url")
    private String itemURL;

    private transient String id;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemURL() {
        return itemURL;
    }

    public void setItemURL(String itemURL) {
        this.itemURL = itemURL;
    }

    public String getId() {
        // https://pokeapi.co/api/v2/item/1/
        // 30 is / before id
        return itemURL.substring(31,itemURL.length()-1);
    }

    public void setId(String id) {
        this.id = itemURL.substring(31,itemURL.length()-1);
    }
}

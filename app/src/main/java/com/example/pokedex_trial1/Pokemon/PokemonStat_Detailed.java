package com.example.pokedex_trial1.Pokemon;

import com.google.gson.annotations.SerializedName;

public class PokemonStat_Detailed {
    @SerializedName("name")
    private String statName;
    @SerializedName("url")
    private String statURL;

    public String getStatName() {
        return statName;
    }

    public void setStatName(String statName) {
        this.statName = statName;
    }

    public String getStatURL() {
        return statURL;
    }

    public void setStatURL(String statURL) {
        this.statURL = statURL;
    }
}

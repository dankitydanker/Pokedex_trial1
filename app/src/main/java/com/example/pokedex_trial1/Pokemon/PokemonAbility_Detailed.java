package com.example.pokedex_trial1.Pokemon;

import com.google.gson.annotations.SerializedName;

public class PokemonAbility_Detailed {
    @SerializedName("name")
    private String abilityName;
    @SerializedName("url")
    private String abilityURL;

    private String abilityId;

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public String getAbilityURL() {
        return abilityURL;
    }

    public void setAbilityURL(String abilityURL) {
        this.abilityURL = abilityURL;
    }

    public String getAbilityId() {
        // https://pokeapi.co/api/v2/ability/31/
        // 33 is / before abilityId
        return abilityURL.substring(34, abilityURL.length() - 1);
    }

    public void setAbilityId(String abilityId) {
        this.abilityId = abilityURL.substring(34, abilityURL.length() - 1);
    }
}

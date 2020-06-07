package com.example.pokedex_trial1.Pokemon;

import com.google.gson.annotations.SerializedName;

public class PokemonSpecies_Broad {
    @SerializedName("name")
    private String speciesName;
    @SerializedName("url")
    private String speciesURL;

    private transient String speciesId;

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public String getSpeciesURL() {
        return speciesURL;
    }

    public void setSpeciesURL(String speciesURL) {
        this.speciesURL = speciesURL;
    }

    public String getSpeciesId() {
        // https://pokeapi.co/api/v2/pokemon-species/25/
        // 41 is / before speciesId
        return speciesURL.substring(42, speciesURL.length() - 1);
    }

    public void setSpeciesId(String speciesId) {
        this.speciesId = speciesURL.substring(42, speciesURL.length() - 1);
    }
}

package com.example.pokedex_trial1.Pokemon;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EvoChain {
    @SerializedName("species")
    private PokemonSpecies_Broad evoSpecies;
    @SerializedName("evolves_to")
    private ArrayList<EvoChain> subEvoChains;

    public PokemonSpecies_Broad getEvoSpecies() {
        return evoSpecies;
    }

    public void setEvoSpecies(PokemonSpecies_Broad evoSpecies) {
        this.evoSpecies = evoSpecies;
    }

    public ArrayList<EvoChain> getSubEvoChains() {
        return subEvoChains;
    }

    public void setSubEvoChains(ArrayList<EvoChain> subEvoChains) {
        this.subEvoChains = subEvoChains;
    }
}

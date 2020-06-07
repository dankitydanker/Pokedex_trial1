package com.example.pokedex_trial1.Pokemon;

import com.google.gson.annotations.SerializedName;

public class PokemonType_Broad {
    @SerializedName("slot")
    private int slot;
    @SerializedName("type")
    private PokemonType_Detailed pokemonType_detailed;

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public PokemonType_Detailed getPokemonType_detailed() {
        return pokemonType_detailed;
    }

    public void setPokemonType_detailed(PokemonType_Detailed pokemonType_detailed) {
        this.pokemonType_detailed = pokemonType_detailed;
    }
}

package com.example.pokedex_trial1.DataClasses;

import com.google.gson.annotations.SerializedName;

public class PokemonTypeList {

    private int slot;
    @SerializedName("pokemon")
    private PokemonListDetail pokemon;

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public PokemonListDetail getPokemon() {
        return pokemon;
    }

    public void setPokemon(PokemonListDetail pokemon) {
        this.pokemon = pokemon;
    }
}

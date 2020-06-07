package com.example.pokedex_trial1.Pokemon;

import com.google.gson.annotations.SerializedName;

public class PokemonStat_Broad {
    @SerializedName("base_stat")
    private int base_stat;
    @SerializedName("effort")
    private int effort;
    @SerializedName("stat")
    private PokemonStat_Detailed pokemonStat_detailed;

    public int getBase_stat() {
        return base_stat;
    }

    public void setBase_stat(int base_stat) {
        this.base_stat = base_stat;
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }

    public PokemonStat_Detailed getPokemonStat_detailed() {
        return pokemonStat_detailed;
    }

    public void setPokemonStat_detailed(PokemonStat_Detailed pokemonStat_detailed) {
        this.pokemonStat_detailed = pokemonStat_detailed;
    }
}

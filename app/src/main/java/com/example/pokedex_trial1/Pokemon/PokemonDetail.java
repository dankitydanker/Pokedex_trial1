package com.example.pokedex_trial1.Pokemon;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PokemonDetail {
    @SerializedName("height")
    private int height;
    @SerializedName("weight")
    private int weight;
    @SerializedName("id")
    private int pokemonId;
    @SerializedName("name")
    private String pokemonName;
    @SerializedName("base_experience")
    private int base_experience;
    @SerializedName("types")
    private ArrayList<PokemonType_Broad> types;
    @SerializedName("stats")
    private ArrayList<PokemonStat_Broad> stats;
    @SerializedName("species")
    private PokemonSpecies_Broad species;
    @SerializedName("abilities")
    private ArrayList<PokemonAbilities_Broad> pokemonAbilities;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(int pokemonId) {
        this.pokemonId = pokemonId;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public int getBase_experience() {
        return base_experience;
    }

    public void setBase_experience(int base_experience) {
        this.base_experience = base_experience;
    }

    public ArrayList<PokemonType_Broad> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<PokemonType_Broad> types) {
        this.types = types;
    }

    public ArrayList<PokemonStat_Broad> getStats() {
        return stats;
    }

    public void setStats(ArrayList<PokemonStat_Broad> stats) {
        this.stats = stats;
    }

    public PokemonSpecies_Broad getSpecies() {
        return species;
    }

    public void setSpecies(PokemonSpecies_Broad species) {
        this.species = species;
    }

    public ArrayList<PokemonAbilities_Broad> getPokemonAbilities() {
        return pokemonAbilities;
    }

    public void setPokemonAbilities(ArrayList<PokemonAbilities_Broad> pokemonAbilities) {
        this.pokemonAbilities = pokemonAbilities;
    }

    @Override
    public String toString() {
        return "PokemonDetail{" +
                "height=" + height +
                ", weight=" + weight +
                ", pokemonId=" + pokemonId +
                ", pokemonName='" + pokemonName + '\'' +
                ", base_experience=" + base_experience +
                ", types=" + types +
                ", stats=" + stats +
                ", species=" + species +
                ", pokemonAbilities=" + pokemonAbilities +
                '}';
    }
}

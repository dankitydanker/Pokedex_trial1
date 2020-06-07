package com.example.pokedex_trial1.DataClasses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PokemonListData {

    private String next;

    private Object previous;
    @SerializedName("results")
    private ArrayList<PokemonListDetail> pokemonListDetails;

    public String getNext() {
        // 33 is /
        // https://pokeapi.co/api/v2/pokemon/?offset=40&limit=20
        //using query so substring doesn't matter
        if (next != null) {
            return next.substring(34);
        } else {
            return next;
        }
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public ArrayList<PokemonListDetail> getPokemonListDetails() {
        return pokemonListDetails;
    }

    public void setPokemonListDetails(ArrayList<PokemonListDetail> pokemonListDetails) {
        this.pokemonListDetails = pokemonListDetails;
    }
}

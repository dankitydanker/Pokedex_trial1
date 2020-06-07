package com.example.pokedex_trial1.DataClasses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PokemonItemsListData {

    private String next;

    private Object previous;
    @SerializedName("results")
    private ArrayList<PokemonItemsListDetail> pokemonItemsDetails;

    public String getNext() {
        return next;
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

    public ArrayList<PokemonItemsListDetail> getPokemonItemsDetails() {
        return pokemonItemsDetails;
    }

    public void setPokemonItemsDetails(ArrayList<PokemonItemsListDetail> pokemonItemsDetails) {
        this.pokemonItemsDetails = pokemonItemsDetails;
    }
}

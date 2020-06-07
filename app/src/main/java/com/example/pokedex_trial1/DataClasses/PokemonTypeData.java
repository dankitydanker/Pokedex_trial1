package com.example.pokedex_trial1.DataClasses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PokemonTypeData {
    @SerializedName("id")
    private int typeId;
    @SerializedName("name")
    private String typeName;
    @SerializedName("pokemon")
    private ArrayList<PokemonTypeList> pokemonTypeList;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public ArrayList<PokemonTypeList> getPokemonTypeList() {
        return pokemonTypeList;
    }

    public void setPokemonTypeList(ArrayList<PokemonTypeList> pokemonTypeList) {
        this.pokemonTypeList = pokemonTypeList;
    }
}


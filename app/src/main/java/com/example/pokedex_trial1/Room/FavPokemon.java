package com.example.pokedex_trial1.Room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fav_pokemon")
public class FavPokemon {
//    @PrimaryKey(autoGenerate = true)
//    private int id;
    @ColumnInfo(name = "fav_pokemon_name")
    private String pokemonName;
    @ColumnInfo(name = "fav_pokemon_id")
    @PrimaryKey
    @NonNull
    private String pokemonId;

    public FavPokemon(String pokemonName, String pokemonId) {
        this.pokemonName = pokemonName;
        this.pokemonId = pokemonId;
    }

//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getId() {
//        return id;
//    }

    public String getPokemonName() {
        return pokemonName;
    }

    public String getPokemonId() {
        return pokemonId;
    }
}

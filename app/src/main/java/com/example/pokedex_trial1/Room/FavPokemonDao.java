package com.example.pokedex_trial1.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface FavPokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavPokemon favPokemon);
    @Delete
    void delete(FavPokemon favPokemon);

    @Query("SELECT * FROM fav_pokemon ORDER BY fav_pokemon_id ASC")
    LiveData<List<FavPokemon>> getAllFavPokemon();

}

package com.example.pokedex_trial1.Room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavPokemonViewModel extends AndroidViewModel {

    private FavPokemonRepository favPokemonRepository;
    private LiveData<List<FavPokemon>> allFavPokemon;


    public FavPokemonViewModel(@NonNull Application application) {
        super(application);

        favPokemonRepository = new FavPokemonRepository(application);
        allFavPokemon = favPokemonRepository.getAllFavPokemon();
    }

    public void insert(FavPokemon favPokemon) {
        favPokemonRepository.insert(favPokemon);
    }

    public void delete(FavPokemon favPokemon) {
        favPokemonRepository.delete(favPokemon);
    }

    public LiveData<List<FavPokemon>> getAllFavPokemon() {
        return allFavPokemon;
    }
}

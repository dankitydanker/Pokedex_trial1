package com.example.pokedex_trial1.Room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FavPokemonRepository {

    private FavPokemonDao favPokemonDao;

    private LiveData<List<FavPokemon>> allFavPokemon;

    public FavPokemonRepository(Application application) {
        FavPokemonDatabase favPokemonDatabase = FavPokemonDatabase.getInstance(application);
        favPokemonDao = favPokemonDatabase.favPokemonDao();
        allFavPokemon = favPokemonDao.getAllFavPokemon();
    }

    public void insert(FavPokemon favPokemon) {
        new InsertFavPokemonAST(favPokemonDao).execute(favPokemon);
    }

    public void delete(FavPokemon favPokemon) {
        new DeleteFavPokemonAST(favPokemonDao).execute(favPokemon);
    }

    public LiveData<List<FavPokemon>> getAllFavPokemon() {
        return allFavPokemon;
    }

    private static class InsertFavPokemonAST extends AsyncTask<FavPokemon, Void, Void> {
        private FavPokemonDao favPokemonDao;

        private InsertFavPokemonAST(FavPokemonDao favPokemonDao) {
            this.favPokemonDao = favPokemonDao;
        }

        @Override
        protected Void doInBackground(FavPokemon... favPokemons) {
            favPokemonDao.insert(favPokemons[0]);
            return null;
        }
    }

    private static class DeleteFavPokemonAST extends AsyncTask<FavPokemon, Void, Void> {
        private FavPokemonDao favPokemonDao;

        private DeleteFavPokemonAST(FavPokemonDao favPokemonDao) {
            this.favPokemonDao = favPokemonDao;
        }

        @Override
        protected Void doInBackground(FavPokemon... favPokemons) {
            favPokemonDao.delete(favPokemons[0]);
            return null;
        }
    }
}

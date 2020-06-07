package com.example.pokedex_trial1.Room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {FavPokemon.class}, version = 2)
public abstract class FavPokemonDatabase extends RoomDatabase {

    private static FavPokemonDatabase instance;

    public abstract FavPokemonDao favPokemonDao();

    public static synchronized FavPokemonDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FavPokemonDatabase.class, "fav_pokemon_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAST(instance).execute();
        }
    };

    private static class PopulateDBAST extends AsyncTask<Void, Void, Void> {
        FavPokemonDao favPokemonDao;

        private PopulateDBAST(FavPokemonDatabase db) {
            favPokemonDao = db.favPokemonDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favPokemonDao.insert(new FavPokemon("pikachu", "25"));
            return null;
        }
    }

}

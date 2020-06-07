package com.example.pokedex_trial1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pokedex_trial1.RVAdapters.FavPokemonRVAdapter;
import com.example.pokedex_trial1.Room.FavPokemon;
import com.example.pokedex_trial1.Room.FavPokemonViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavPokemonActivity extends AppCompatActivity {
    private FavPokemonViewModel favPokemonViewModel;

    RecyclerView rvFavPokemon;
    RecyclerView.LayoutManager rvFavPokemonLM;
    FavPokemonRVAdapter rvFavPokemonAdapter;

    List<FavPokemon> favPokemonList;

    Fade fade;
    View decor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_pokemon);

        rvFavPokemon = findViewById(R.id.rvFavPokemon);
        rvFavPokemon.setHasFixedSize(true);
        rvFavPokemonLM = new LinearLayoutManager(this);
        rvFavPokemon.setLayoutManager(rvFavPokemonLM);
        rvFavPokemonAdapter = new FavPokemonRVAdapter();
        rvFavPokemon.setAdapter(rvFavPokemonAdapter);

        fade = new Fade();
        decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        favPokemonList = new ArrayList<>();

        favPokemonViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(FavPokemonViewModel.class);
        favPokemonViewModel.getAllFavPokemon().observe(this, new Observer<List<FavPokemon>>() {
            @Override
            public void onChanged(List<FavPokemon> favPokemons) {
                //Toast.makeText(FavPokemonActivity.this, "onChanged: " + favPokemons.size(), Toast.LENGTH_SHORT).show();
                favPokemonList = favPokemons;
                rvFavPokemonAdapter.setFavPokemon(favPokemonList);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                favPokemonViewModel.delete(rvFavPokemonAdapter.getFavPokemonAt(viewHolder.getAdapterPosition()));
                Toast.makeText(FavPokemonActivity.this, "Deleted Pokemon", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rvFavPokemon);

        rvFavPokemonAdapter.setOnFavPokemonClickListener(new FavPokemonRVAdapter.OnFavPokemonClickListener() {
            @Override
            public void onFavPokemonClick(int position, ImageView sharedImageView) {
                FavPokemon selectedPokemon = favPokemonList.get(position);
                String pokemonName = selectedPokemon.getPokemonName();
                Toast.makeText(FavPokemonActivity.this, pokemonName, Toast.LENGTH_SHORT).show();
                Intent pokemonDetail = new Intent(FavPokemonActivity.this, PokemonDetailActivity.class);
                pokemonDetail.putExtra("POKEMON_ID", selectedPokemon.getPokemonId());
                pokemonDetail.putExtra("POKEMON_NAME", selectedPokemon.getPokemonName());
                pokemonDetail.putExtra("FAV", true);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        FavPokemonActivity.this,
                        sharedImageView,
                        ViewCompat.getTransitionName(sharedImageView));

                startActivity(pokemonDetail, options.toBundle());
            }
        });
    }
}

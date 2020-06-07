package com.example.pokedex_trial1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pokedex_trial1.Pokemon.EvoChain;
import com.example.pokedex_trial1.Pokemon.EvolutionChainMain;
import com.example.pokedex_trial1.Pokemon.PokemonSpecies_Detailed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EvoChainActivity extends AppCompatActivity {

    private static final String TAG = "GetPokemonEvolution";
    private static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";
    private static final String POKEMON_IMAGE_BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";

    String pokemonId;

    PokeApi evoPokeApi;

    PokemonSpecies_Detailed pokemonSpecies_detailed;
    EvolutionChainMain mainEvoChain;

    LinearLayout llEvoChain;
    FrameLayout flEvoChain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evo_chain);
        setTitle("Pokemon Evolution");

        llEvoChain = findViewById(R.id.llEvoChain);
        flEvoChain = findViewById(R.id.flEvoChain);

        pokemonId = getIntent().getStringExtra("POKEMON_ID");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POKEAPI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        evoPokeApi = retrofit.create(PokeApi.class);

        getPokemonSpecies(pokemonId);

    }

    private void getPokemonSpecies(String pokemonId) {

        Call<PokemonSpecies_Detailed> pokemonSpeciesDetailedCall = evoPokeApi.getPokemonSpecies(pokemonId);

        pokemonSpeciesDetailedCall.enqueue(new Callback<PokemonSpecies_Detailed>() {
            @Override
            public void onResponse(Call<PokemonSpecies_Detailed> call, Response<PokemonSpecies_Detailed> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Code:" + response.code());
                    return;
                }
                pokemonSpecies_detailed = response.body();
                String evoChainID;
                Log.i(TAG, "onResponse: EvoURL:" + pokemonSpecies_detailed.getEvolution_chain().getEvoChainURL());
                evoChainID = pokemonSpecies_detailed.getEvolution_chain().getEvoChainId();
                getEvolutionChain(evoChainID);
            }

            @Override
            public void onFailure(Call<PokemonSpecies_Detailed> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void getEvolutionChain(String evoChainID) {

        Call<EvolutionChainMain> evolutionChainMainCall = evoPokeApi.getPokemonEvoChain(evoChainID);

        evolutionChainMainCall.enqueue(new Callback<EvolutionChainMain>() {
            @Override
            public void onResponse(Call<EvolutionChainMain> call, Response<EvolutionChainMain> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Code:" + response.code());
                    return;
                }
                mainEvoChain = response.body();
                Log.i(TAG, "onResponse: mainEvoChainId: " + mainEvoChain.getEvoChainId());
                getEvolutionDetails();

            }

            @Override
            public void onFailure(Call<EvolutionChainMain> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void getEvolutionDetails() {

        EvoChain evoChainBeginning = mainEvoChain.getEvoChainBeginning();
        showSpecies(evoChainBeginning);
        Log.i(TAG, "getEvolutionDetails: Beginning: " + evoChainBeginning.getEvoSpecies().getSpeciesName());
        ArrayList<EvoChain> evoChains = evoChainBeginning.getSubEvoChains(); // size is max. 1

        while (evoChains.size() > 0) {
            EvoChain subEvoChains = evoChains.get(0);
            Log.i(TAG, "getEvolutionDetails: Species:" + subEvoChains.getEvoSpecies().getSpeciesName());
            showSpecies(subEvoChains);
            evoChains = subEvoChains.getSubEvoChains();
        }


//        for (EvoChain subEvoChains : evoChains) {
//            // above is the actual first sub evo chain
//
//            Log.i(TAG, "getEvolutionDetails: Species:" + subEvoChains.getEvoSpecies().getSpeciesName());
//            evoChains = subEvoChains.getSubEvoChains();
//            continue;
//
//        }

    }

    private void showSpecies(EvoChain subEvoChains) {
        ImageView pokemonImage = new ImageView(this);
        TextView pokemonName = new TextView(this);

        LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(450, 450);
        //ivParams.weight = 1;
        ivParams.gravity = Gravity.CENTER_HORIZONTAL;
        ivParams.setMargins(10, 10, 10, 10);
        pokemonImage.setScaleType(ImageView.ScaleType.FIT_XY);
        pokemonImage.setLayoutParams(ivParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pokemonImage.setForegroundGravity(Gravity.CENTER_HORIZONTAL);
        }
        llEvoChain.addView(pokemonImage);

        Picasso.get().load(POKEMON_IMAGE_BASE_URL + subEvoChains.getEvoSpecies().getSpeciesId() + ".png")
                .placeholder(R.drawable.rsz_whosthatpokemon)
                .into(pokemonImage);

        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tvParams.setMargins(10, 10, 10, 10);
        tvParams.weight = 1;
        tvParams.gravity = Gravity.CENTER_HORIZONTAL;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pokemonName.setTextAppearance(R.style.PokemonEvolutionNames);
        }
        pokemonName.setLayoutParams(tvParams);
        llEvoChain.addView(pokemonName);
        pokemonName.setText(subEvoChains.getEvoSpecies().getSpeciesName());

    }
}

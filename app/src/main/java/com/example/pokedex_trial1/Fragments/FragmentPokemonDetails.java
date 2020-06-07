package com.example.pokedex_trial1.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pokedex_trial1.EvoChainActivity;
import com.example.pokedex_trial1.PokeApi;
import com.example.pokedex_trial1.Pokemon.PokemonAbilities_Broad;
import com.example.pokedex_trial1.Pokemon.PokemonDetail;
import com.example.pokedex_trial1.Pokemon.PokemonStat_Broad;
import com.example.pokedex_trial1.Pokemon.PokemonType_Broad;
import com.example.pokedex_trial1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentPokemonDetails extends Fragment {
    View fragView;
    Context context;
    String pokemonId;

    private static final String POKEMON_IMAGE_BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
    private static final String TAG = "GetPokemonDetail";
    public static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";

    PokemonDetail selectedPokemonDetail;

    PokeApi detailPokeApi;

    ImageView ivDetailPokemonImage;
    TextView tvDetailPokemonName, tvDetailHeight, tvDetailWeight, tvDetailXP, tvDetailAbilities, tvDetailTypes;
    Button buShowStats, buShowEvo;
    Dialog statsDialog;
    TextView tvStatsHP, tvStatsSpeed, tvStatsAtt, tvStatsDef, tvStatsSpAtt, tvStatsSpDef;

    ArrayList<PokemonStat_Broad> pokemonStats;

    public FragmentPokemonDetails(Context context, String pokemonId) {
        this.context = context;
        this.pokemonId = pokemonId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_pokemon_detail, container, false);

        ivDetailPokemonImage = fragView.findViewById(R.id.ivDetailPokemonImage);
        tvDetailPokemonName = fragView.findViewById(R.id.tvDetailPokemonName);
        tvDetailHeight = fragView.findViewById(R.id.tvDetailHeight);
        tvDetailWeight = fragView.findViewById(R.id.tvDetailWeight);
        tvDetailXP = fragView.findViewById(R.id.tvDetailXP);
        tvDetailAbilities = fragView.findViewById(R.id.tvDetailAbilities);
        tvDetailTypes = fragView.findViewById(R.id.tvDetailTypes);
        buShowStats = fragView.findViewById(R.id.buFragPokemonStats);
        buShowEvo = fragView.findViewById(R.id.buFragPokemonEvo);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POKEAPI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        detailPokeApi = retrofit.create(PokeApi.class);

        statsDialog = new Dialog(getActivity());

        getPokemonDetails();

        buShowStats.setOnClickListener(this::showStats);
        buShowEvo.setOnClickListener(this::showEvolution);


        return fragView;
    }

    private void getPokemonDetails() {
        Call<PokemonDetail> pokemonDetailCall = detailPokeApi.getPokemonDetail(pokemonId);

        pokemonDetailCall.enqueue(new retrofit2.Callback<PokemonDetail>() {
            @Override
            public void onResponse(Call<PokemonDetail> call, Response<PokemonDetail> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Code:" + response.code());
                    return;
                }
                selectedPokemonDetail = response.body();
                Log.i(TAG, "onResponse: " + selectedPokemonDetail.getPokemonName());
                fillPokemonDetails();
            }

            @Override
            public void onFailure(Call<PokemonDetail> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void fillPokemonDetails() {
        Picasso.get().load(POKEMON_IMAGE_BASE_URL + selectedPokemonDetail.getPokemonId() + ".png")
                .placeholder(R.drawable.rsz_whosthatpokemon)
                .into(ivDetailPokemonImage);

        tvDetailPokemonName.setText(selectedPokemonDetail.getPokemonName());
        tvDetailHeight.setText(selectedPokemonDetail.getHeight() + " dm");
        tvDetailWeight.setText(selectedPokemonDetail.getWeight() + " hg");
        tvDetailXP.setText("" + selectedPokemonDetail.getBase_experience());

        for (PokemonAbilities_Broad pokemonAbility : selectedPokemonDetail.getPokemonAbilities()) {
            String abilityName = pokemonAbility.getPokemonAbility_detailed().getAbilityName().toUpperCase();
            tvDetailAbilities.append(abilityName+"\n");
        }

        for (PokemonType_Broad pokemonType : selectedPokemonDetail.getTypes()) {
            String typeName = pokemonType.getPokemonType_detailed().getTypeName().toUpperCase();
            tvDetailTypes.append(typeName+"\n");
        }

        pokemonStats = selectedPokemonDetail.getStats();
    }

    public void showStats(View view) {

        statsDialog.setContentView(R.layout.fragment_pokemon_stats);

        tvStatsHP = statsDialog.findViewById(R.id.tvStatsHP);
        tvStatsSpeed = statsDialog.findViewById(R.id.tvStatsSpeed);
        tvStatsAtt = statsDialog.findViewById(R.id.tvStatsAtt);
        tvStatsDef = statsDialog.findViewById(R.id.tvStatsDef);
        tvStatsSpAtt = statsDialog.findViewById(R.id.tvStatsSpAtt);
        tvStatsSpDef = statsDialog.findViewById(R.id.tvStatsSpDef);
        fillPokemonStats();

        statsDialog.show();
    }

    private void fillPokemonStats() {

        tvStatsHP.setText(""+pokemonStats.get(5).getBase_stat());
        tvStatsSpeed.setText(""+pokemonStats.get(0).getBase_stat());
        tvStatsAtt.setText(""+pokemonStats.get(4).getBase_stat());
        tvStatsDef.setText(""+pokemonStats.get(3).getBase_stat());
        tvStatsSpAtt.setText(""+pokemonStats.get(2).getBase_stat());
        tvStatsSpDef.setText(""+pokemonStats.get(1).getBase_stat());

    }

    public void showEvolution(View view) {
        Intent evoActivity = new Intent(context, EvoChainActivity.class);
        evoActivity.putExtra("POKEMON_ID", pokemonId);
        startActivity(evoActivity);
    }
}

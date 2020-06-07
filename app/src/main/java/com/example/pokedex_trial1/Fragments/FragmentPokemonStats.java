package com.example.pokedex_trial1.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pokedex_trial1.Pokemon.PokemonDetail;
import com.example.pokedex_trial1.Pokemon.PokemonStat_Broad;
import com.example.pokedex_trial1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FragmentPokemonStats extends Fragment {

    View fragView;
    Context context;
    PokemonDetail selectedPokemon;
    ArrayList<PokemonStat_Broad> pokemonStats;

    private static final String POKEMON_IMAGE_BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";

    ImageView ivStatsPokemonImage;
    TextView tvStatsPokemonName, tvStatsHP, tvStatsSpeed, tvStatsAtt, tvStatsDef, tvStatsSpAtt, tvStatsSpDef;

    public FragmentPokemonStats(Context context, PokemonDetail selectedPokemon) {
        this.context = context;
        this.selectedPokemon = selectedPokemon;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_pokemon_stats, container, false);

//        ivStatsPokemonImage = fragView.findViewById(R.id.ivStatsPokemonImage);
//        tvStatsPokemonName = fragView.findViewById(R.id.tvStatsPokemonName);
        tvStatsHP = fragView.findViewById(R.id.tvStatsHP);
        tvStatsSpeed = fragView.findViewById(R.id.tvStatsSpeed);
        tvStatsAtt = fragView.findViewById(R.id.tvStatsAtt);
        tvStatsDef = fragView.findViewById(R.id.tvStatsDef);
        tvStatsSpAtt = fragView.findViewById(R.id.tvStatsSpAtt);
        tvStatsSpDef = fragView.findViewById(R.id.tvStatsSpDef);

        fillPokemonStats();

        return fragView;
    }

    private void fillPokemonStats() {

        pokemonStats = selectedPokemon.getStats();

        Picasso.get().load(POKEMON_IMAGE_BASE_URL + selectedPokemon.getPokemonId() + ".png")
                .placeholder(R.drawable.rsz_whosthatpokemon)
                .into(ivStatsPokemonImage);

        tvStatsPokemonName.setText(selectedPokemon.getPokemonName().toUpperCase());

        tvStatsHP.setText(""+pokemonStats.get(5).getBase_stat());
        tvStatsSpeed.setText(""+pokemonStats.get(0).getBase_stat());
        tvStatsAtt.setText(""+pokemonStats.get(4).getBase_stat());
        tvStatsDef.setText(""+pokemonStats.get(3).getBase_stat());
        tvStatsSpAtt.setText(""+pokemonStats.get(2).getBase_stat());
        tvStatsSpDef.setText(""+pokemonStats.get(1).getBase_stat());

    }
}

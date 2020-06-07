package com.example.pokedex_trial1.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pokedex_trial1.R;

public class FragmentPokemonEvolution extends Fragment {
    View fragView;
    Context context;
    int pokemonSpeciesId;

    private static final String TAG = "FragPokeEvolution";

    public FragmentPokemonEvolution(Context context, int pokemonSpeciesId) {
        this.context = context;
        this.pokemonSpeciesId = pokemonSpeciesId;
        Log.i(TAG, "FragmentPokemonEvolution: " + pokemonSpeciesId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_pokemon_evolution, container, false);
        return fragView;
    }
}

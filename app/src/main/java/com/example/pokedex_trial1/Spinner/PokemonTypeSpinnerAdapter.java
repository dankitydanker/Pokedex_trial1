package com.example.pokedex_trial1.Spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pokedex_trial1.R;

import java.util.ArrayList;

public class PokemonTypeSpinnerAdapter extends ArrayAdapter<PokemonTypeSpinnerItem> {

    public PokemonTypeSpinnerAdapter(Context context, ArrayList<PokemonTypeSpinnerItem> pokemonTypes){
        super(context, 0, pokemonTypes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_pokemon_type_row, parent, false);
        }

        ImageView ivSpinnerPokemonType = convertView.findViewById(R.id.ivSpinnerPokemonType);
        TextView tvSpinnerPokemonType = convertView.findViewById(R.id.tvSpinnerPokemonType);

        PokemonTypeSpinnerItem pokemonTypeSpinnerItem = getItem(position);

        if (pokemonTypeSpinnerItem != null) {
            ivSpinnerPokemonType.setImageResource(pokemonTypeSpinnerItem.getTypeImage());
            tvSpinnerPokemonType.setText(pokemonTypeSpinnerItem.getTypeName());
        }

        return convertView;
    }

}

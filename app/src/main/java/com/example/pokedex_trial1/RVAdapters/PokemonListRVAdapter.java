package com.example.pokedex_trial1.RVAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex_trial1.DataClasses.PokemonListDetail;
import com.example.pokedex_trial1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokemonListRVAdapter extends RecyclerView.Adapter<PokemonListRVAdapter.PokemonListViewHolder> {
    private static final String POKEMON_IMAGE_BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
    ArrayList<PokemonListDetail> pokemonList;
    OnPokemonClickListener mListener;

    public PokemonListRVAdapter(ArrayList<PokemonListDetail> pokemonList) {
        this.pokemonList = pokemonList;
    }

    public interface OnPokemonClickListener {
        void OnPokemonClick(int position, ImageView sharedImageView);
    }

    public void setOnPokemonClickListener(OnPokemonClickListener listener) {
        mListener = listener;
    }

    public static class PokemonListViewHolder extends RecyclerView.ViewHolder{

        public TextView tvPokemonName;
        public ImageView ivPokemonImage;

        public PokemonListViewHolder(@NonNull View itemView, final OnPokemonClickListener listener) {
            super(itemView);
            tvPokemonName = itemView.findViewById(R.id.tvPokemonName);
            ivPokemonImage = itemView.findViewById(R.id.ivPokemonImage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnPokemonClick(position, ivPokemonImage);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public PokemonListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_carditem, parent, false);
        PokemonListViewHolder plvh = new PokemonListViewHolder(v, mListener);
        return plvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonListViewHolder holder, int position) {
        PokemonListDetail pokemonDetail = pokemonList.get(position);
        // we get id,name

        holder.tvPokemonName.setText(pokemonDetail.getName());

        ViewCompat.setTransitionName(holder.ivPokemonImage, pokemonDetail.getName());

        Picasso.get().load(POKEMON_IMAGE_BASE_URL + pokemonDetail.getId() + ".png")
                .placeholder(R.drawable.rsz_whosthatpokemon)
                .into(holder.ivPokemonImage);

    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public PokemonListDetail getPokemonAt(int position) {
        return pokemonList.get(position);
    }

}

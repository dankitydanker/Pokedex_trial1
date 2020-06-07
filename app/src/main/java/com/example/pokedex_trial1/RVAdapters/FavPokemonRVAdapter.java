package com.example.pokedex_trial1.RVAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex_trial1.R;
import com.example.pokedex_trial1.Room.FavPokemon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavPokemonRVAdapter extends RecyclerView.Adapter<FavPokemonRVAdapter.FavPokemonVH> {
    private List<FavPokemon> favPokemonList = new ArrayList<>();
    OnFavPokemonClickListener mListener;

    private static final String POKEMON_IMAGE_BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";

    class FavPokemonVH extends RecyclerView.ViewHolder{
        TextView tvPokemonName;
        ImageView ivPokemonImage;

        public FavPokemonVH(@NonNull View itemView, final OnFavPokemonClickListener listener) {
            super(itemView);

            tvPokemonName = itemView.findViewById(R.id.tvPokemonName);
            ivPokemonImage = itemView.findViewById(R.id.ivPokemonImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onFavPokemonClick(position, ivPokemonImage);
                        }
                    }
                }
            });
        }
    }

    public interface OnFavPokemonClickListener {
        void onFavPokemonClick(int position, ImageView sharedImageView);
    }

    public void setOnFavPokemonClickListener(OnFavPokemonClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public FavPokemonVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_carditem, parent, false);
        FavPokemonVH fpvh = new FavPokemonVH(v, mListener);
        return fpvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FavPokemonVH holder, int position) {
        FavPokemon favPokemon = favPokemonList.get(position);

        holder.tvPokemonName.setText(favPokemon.getPokemonName());

        ViewCompat.setTransitionName(holder.ivPokemonImage, favPokemon.getPokemonName());

        Picasso.get().load(POKEMON_IMAGE_BASE_URL + favPokemon.getPokemonId() + ".png")
                .placeholder(R.drawable.rsz_whosthatpokemon)
                .into(holder.ivPokemonImage);
    }

    @Override
    public int getItemCount() {
        return favPokemonList.size();
    }

    public void setFavPokemon(List<FavPokemon> favPokemonList) {
        this.favPokemonList = favPokemonList;
        notifyDataSetChanged();
    }

    public FavPokemon getFavPokemonAt(int position) {
        return favPokemonList.get(position);
    }

}

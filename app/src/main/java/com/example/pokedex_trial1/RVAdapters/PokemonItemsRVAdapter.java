package com.example.pokedex_trial1.RVAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex_trial1.DataClasses.PokemonItemsListDetail;
import com.example.pokedex_trial1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokemonItemsRVAdapter extends RecyclerView.Adapter<PokemonItemsRVAdapter.PokemonItemsViewHolder> {
    // add ".png" at the end
    private static final String POKEMON_ITEM_IMAGE_BASE_URL= "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/";
    private ArrayList<PokemonItemsListDetail> pokemonItemsList;
    OnPokeItemClickListener mListener;

    public PokemonItemsRVAdapter(ArrayList<PokemonItemsListDetail> pokemonItemsList) {
        this.pokemonItemsList = pokemonItemsList;
    }

    public interface OnPokeItemClickListener {
        void OnPokeItemClick(int position, ImageView sharedImageView);
    }

    public void setOnPokeItemClickListener(OnPokeItemClickListener listener) {
        mListener = listener;
    }

    public static class PokemonItemsViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItemName;
        private ImageView ivItemImage;

        public PokemonItemsViewHolder(@NonNull View itemView, final OnPokeItemClickListener listener) {
            super(itemView);

            tvItemName = itemView.findViewById(R.id.tvItemName);
            ivItemImage = itemView.findViewById(R.id.ivItemImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnPokeItemClick(position, ivItemImage);
                        }
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public PokemonItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_carditem, parent, false);
        PokemonItemsViewHolder pivh = new PokemonItemsViewHolder(v, mListener);
        return pivh;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonItemsViewHolder holder, int position) {
        PokemonItemsListDetail pokemonItem = pokemonItemsList.get(position);

        holder.tvItemName.setText(pokemonItem.getItemName());

        ViewCompat.setTransitionName(holder.ivItemImage, pokemonItem.getItemName());

        Picasso.get().load(POKEMON_ITEM_IMAGE_BASE_URL + pokemonItem.getItemName() + ".png" )
                .placeholder(R.drawable.question_mark_pixels_100px)
                .into(holder.ivItemImage);

    }

    @Override
    public int getItemCount() {
        return pokemonItemsList.size();
    }

}

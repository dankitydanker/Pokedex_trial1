package com.example.pokedex_trial1.RVAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex_trial1.MiscStuffClasses.LocationListDetail;
import com.example.pokedex_trial1.R;

import java.util.ArrayList;

public class PokemonLocationsRVAdapter extends RecyclerView.Adapter<PokemonLocationsRVAdapter.PokemonLocationsVH> {

    private ArrayList<LocationListDetail> locationsList;
    OnLocClickListener mListener;

    public PokemonLocationsRVAdapter(ArrayList<LocationListDetail> locationsList) {
        this.locationsList = locationsList;
    }

    public interface OnLocClickListener{
        void OnLocClick(int position);
    }

    public void setOnLocClickListener(OnLocClickListener listener) {
        mListener = listener;
    }

    public static class PokemonLocationsVH extends RecyclerView.ViewHolder{

        private ImageView ivLocationImage;
        private TextView tvLocationName;


        public PokemonLocationsVH(@NonNull View itemView, OnLocClickListener listener) {
            super(itemView);

            ivLocationImage = itemView.findViewById(R.id.ivItemImage);
            tvLocationName = itemView.findViewById(R.id.tvItemName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnLocClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public PokemonLocationsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_carditem, parent, false);
        PokemonLocationsVH plvh = new PokemonLocationsVH(v, mListener);
        return plvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonLocationsVH holder, int position) {
        LocationListDetail location = locationsList.get(position);

        holder.tvLocationName.setText(location.getLocationName());
    }

    @Override
    public int getItemCount() {
        return locationsList.size();
    }

}

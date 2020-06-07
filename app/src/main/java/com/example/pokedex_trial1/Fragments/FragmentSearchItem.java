package com.example.pokedex_trial1.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pokedex_trial1.MiscStuffClasses.PokeItemDetail;
import com.example.pokedex_trial1.PokeApi;
import com.example.pokedex_trial1.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentSearchItem extends Fragment {
    View fragView;
    Context context;
    String itemId;

    private static final String ITEM_IMAGE_BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/";
    private static final String TAG = "GetItemDetail";
    public static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";

    ImageView ivSearchItemImage;
    TextView tvSearchItemName, tvSearchItemDesc;

    PokeApi itemPokeApi;

    PokeItemDetail pokeItemDetail;

    public FragmentSearchItem(Context context, String itemId) {
        this.context = context;
        this.itemId = itemId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_search_poke_item, container, false);

        ivSearchItemImage = fragView.findViewById(R.id.ivSearchItemImage);
        tvSearchItemName = fragView.findViewById(R.id.tvSearchItemName);
        tvSearchItemDesc = fragView.findViewById(R.id.tvSearchItemDesc);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POKEAPI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        itemPokeApi = retrofit.create(PokeApi.class);

        getItemDetails();

        return fragView;
    }

    private void getItemDetails() {
        Call<PokeItemDetail> pokeItemDetailCall = itemPokeApi.getPokeItemDetail(itemId);

        pokeItemDetailCall.enqueue(new Callback<PokeItemDetail>() {
            @Override
            public void onResponse(Call<PokeItemDetail> call, Response<PokeItemDetail> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Code:" + response.code());
                    return;
                }

                pokeItemDetail = response.body();
                fillItemDetails();
            }

            @Override
            public void onFailure(Call<PokeItemDetail> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void fillItemDetails() {
        Picasso.get().load(ITEM_IMAGE_BASE_URL + pokeItemDetail.getItemName() + ".png")
                .placeholder(R.drawable.question_mark_pixels_100px)
                .into(ivSearchItemImage);

        tvSearchItemName.setText(pokeItemDetail.getItemName());
        tvSearchItemDesc.setText(pokeItemDetail.getItemEffectEntries().get(0).getEffect());
    }
}

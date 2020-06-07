package com.example.pokedex_trial1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokedex_trial1.MiscStuffClasses.PokeItemDetail;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemDetailActivity extends AppCompatActivity {

    String itemId;
    String itemName;

    private static final String ITEM_IMAGE_BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/";
    private static final String TAG = "GetItemDetail";
    public static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";

    ImageView ivSearchItemImage;
    TextView tvSearchItemName, tvSearchItemDesc;

    PokeApi itemPokeApi;

    PokeItemDetail pokeItemDetail;

    Fade fade;
    View decor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_item);
        supportPostponeEnterTransition();

        itemId = getIntent().getStringExtra("ITEM_ID");
        itemName = getIntent().getStringExtra("ITEM_NAME");

        ivSearchItemImage = findViewById(R.id.ivSearchItemImage);
        tvSearchItemName = findViewById(R.id.tvSearchItemName);
        tvSearchItemDesc = findViewById(R.id.tvSearchItemDesc);

        fade = new Fade();
        decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POKEAPI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        itemPokeApi = retrofit.create(PokeApi.class);

        ivSearchItemImage.setTransitionName(itemName);

        Picasso.get().load(ITEM_IMAGE_BASE_URL + itemName + ".png")
                .noFade()
                .placeholder(R.drawable.question_mark_pixels_100px)
                .into(ivSearchItemImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError(Exception e) {
                        supportStartPostponedEnterTransition();
                    }
                });

        getItemDetails();
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
        tvSearchItemName.setText(pokeItemDetail.getItemName());
        tvSearchItemDesc.setText(pokeItemDetail.getItemEffectEntries().get(0).getEffect());
    }
}

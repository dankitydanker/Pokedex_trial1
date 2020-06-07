package com.example.pokedex_trial1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokedex_trial1.DataClasses.PokemonListData;
import com.example.pokedex_trial1.DataClasses.PokemonListDetail;
import com.example.pokedex_trial1.Pokemon.PokemonAbilities_Broad;
import com.example.pokedex_trial1.Pokemon.PokemonDetail;
import com.example.pokedex_trial1.Pokemon.PokemonStat_Broad;
import com.example.pokedex_trial1.Pokemon.PokemonType_Broad;
import com.example.pokedex_trial1.RVAdapters.PokemonListRVAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetailActivity extends AppCompatActivity {

    private static final String TAG = "GetPokemonDetail";
    private static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";

    private static final String POKEMON_IMAGE_BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
    String pokemonId;
    String pokemonName;

    PokemonDetail selectedPokemonDetail;
    PokeApi detailPokeApi;

    ArrayList<PokemonStat_Broad> pokemonStats;

    ImageView ivDetailPokemonImage;
    TextView tvDetailPokemonName, tvDetailHeight, tvDetailWeight, tvDetailXP, tvDetailAbilities, tvDetailTypes;
    TextView tvStatsHP, tvStatsSpeed, tvStatsAtt, tvStatsDef, tvStatsSpAtt, tvStatsSpDef;
    ImageButton ibSharePokemon;

    Fade fade;
    View decor;

    Dialog statsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);
        setTitle("Pokemon Detail");
        supportPostponeEnterTransition();

        pokemonId = getIntent().getStringExtra("POKEMON_ID");
        pokemonName = getIntent().getStringExtra("POKEMON_NAME");

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

        detailPokeApi = retrofit.create(PokeApi.class);

        ivDetailPokemonImage = findViewById(R.id.ivDetailPokemonImage);
        tvDetailPokemonName = findViewById(R.id.tvDetailPokemonName);
        tvDetailHeight = findViewById(R.id.tvDetailHeight);
        tvDetailWeight = findViewById(R.id.tvDetailWeight);
        tvDetailXP = findViewById(R.id.tvDetailXP);
        tvDetailAbilities = findViewById(R.id.tvDetailAbilities);
        tvDetailTypes = findViewById(R.id.tvDetailTypes);

        ibSharePokemon = findViewById(R.id.ibSharePokemon);
        if (getIntent().hasExtra("FAV")) {
            ibSharePokemon.setVisibility(View.VISIBLE);
        }

        ivDetailPokemonImage.setTransitionName(pokemonName);

        Picasso.get()
                .load(POKEMON_IMAGE_BASE_URL + pokemonId + ".png")
                .noFade()
                .into(ivDetailPokemonImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError(Exception e) {
                        supportStartPostponedEnterTransition();
                    }
                });

        tvDetailPokemonName.setText(pokemonName.toUpperCase());

        statsDialog = new Dialog(this);

        getPokemonDetails();

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
        Intent evoActivity = new Intent(this, EvoChainActivity.class);
        evoActivity.putExtra("POKEMON_ID", pokemonId);
        startActivity(evoActivity);
    }

    public void sharePokemon(View view) {
        Drawable drawable=ivDetailPokemonImage.getDrawable();
        Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();

        String pokemonDetails = getShareablePokemonDetails();

        try {
            File file = new File(getApplicationContext().getExternalCacheDir(), File.separator +"pokemonimg.jpg");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID +".provider", file);
            intent.putExtra(Intent.EXTRA_TEXT, pokemonDetails);
            intent.putExtra(Intent.EXTRA_STREAM, photoURI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/jpg");

            startActivity(Intent.createChooser(intent, "Share Pokemon via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getShareablePokemonDetails() {
        String details = "Name: " + tvDetailPokemonName.getText().toString() + "\n"
                + "Height: " + tvDetailHeight.getText().toString() + "\n"
                + "Weight: " + tvDetailWeight.getText().toString() + "\n"
                + "Base XP: " + tvDetailXP.getText().toString() + "\n"
                + "Abilities: " + tvDetailAbilities.getText().toString() // already a newline is present
                + "Type(s): " + tvDetailTypes.getText().toString()  ;// already a newline is present

        return details;
    }
}

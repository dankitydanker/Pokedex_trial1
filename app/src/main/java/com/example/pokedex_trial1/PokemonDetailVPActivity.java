package com.example.pokedex_trial1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.example.pokedex_trial1.Fragments.FragmentPokemonDetails;
import com.example.pokedex_trial1.Fragments.FragmentPokemonEvolution;
import com.example.pokedex_trial1.Fragments.FragmentPokemonStats;
import com.example.pokedex_trial1.Pokemon.PokemonDetail;
import com.example.pokedex_trial1.ViewPagerAdapter.PokemonDetailVPAdapter;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetailVPActivity extends AppCompatActivity {

    private TabLayout tlPokemonDetail;
    private ViewPager vpPokemonDetail;

    PokemonDetailVPAdapter adapter;

    public static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";
    private static final String TAG = "PokemonDetailVPActivity";

    PokeApi detailPokeApi;

    String pokemonId;

    PokemonDetail selectedPokemonDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_vp_detail);

        pokemonId = getIntent().getStringExtra("POKEMON_ID");

        tlPokemonDetail = findViewById(R.id.tlPokemonDetail);
        vpPokemonDetail = findViewById(R.id.vpPokemonDetail);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POKEAPI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        detailPokeApi = retrofit.create(PokeApi.class);

        getPokemonDetail();

    }

    private void getPokemonDetail() {
        Call<PokemonDetail> pokemonDetailCall = detailPokeApi.getPokemonDetail(pokemonId);

        pokemonDetailCall.enqueue(new Callback<PokemonDetail>() {
            @Override
            public void onResponse(Call<PokemonDetail> call, Response<PokemonDetail> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Code:" + response.code());
                    return;
                }
                selectedPokemonDetail = response.body();
                Log.i(TAG, "onResponse: " + selectedPokemonDetail.getPokemonName());
                setupAdapter();
            }

            @Override
            public void onFailure(Call<PokemonDetail> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void setupAdapter() {
        adapter = new PokemonDetailVPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new FragmentPokemonDetails(getApplicationContext(), pokemonId), "Details");
        adapter.addFragment(new FragmentPokemonStats(getApplicationContext(), selectedPokemonDetail), "Stats");
        adapter.addFragment(new FragmentPokemonEvolution(getApplicationContext(), selectedPokemonDetail.getPokemonId()), "Evo-Chain");

        vpPokemonDetail.setAdapter(adapter);
        tlPokemonDetail.setupWithViewPager(vpPokemonDetail);
    }
}

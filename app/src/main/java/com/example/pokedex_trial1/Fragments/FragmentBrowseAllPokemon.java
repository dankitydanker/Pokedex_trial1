package com.example.pokedex_trial1.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex_trial1.PokemonDetailActivity;
import com.example.pokedex_trial1.DataClasses.PokemonListData;
import com.example.pokedex_trial1.DataClasses.PokemonListDetail;
import com.example.pokedex_trial1.PokeApi;
import com.example.pokedex_trial1.RVAdapters.PokemonListRVAdapter;
import com.example.pokedex_trial1.R;
import com.example.pokedex_trial1.Room.FavPokemon;
import com.example.pokedex_trial1.Room.FavPokemonViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentBrowseAllPokemon extends Fragment {

    Context context;

    private FavPokemonViewModel favPokemonViewModel;

    public FragmentBrowseAllPokemon(Context context) {
        this.context = context;
    }

    View fragView;
    private static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";
    private static final String TAG = "FragAllPokemon";

    RecyclerView rvBrowseAllPokemon;
    RecyclerView.LayoutManager rvBrowseAllPokemonLM;
    PokemonListRVAdapter rvBrowseAllPokemonAdapter;

    ArrayList<PokemonListDetail> mainPokemonDetailsList;

    PokeApi fragAllPokeApi;

    PokemonListData currentPokemonListData;

    int offset;

    Fade fade;
    View decor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Browse All Pokemon");
        fragView = inflater.inflate(R.layout.fragment_browse_all_pokemon, container, false);

        mainPokemonDetailsList = new ArrayList<>();

        rvBrowseAllPokemon = fragView.findViewById(R.id.rvBrowseAllPokemon);
        rvBrowseAllPokemon.setHasFixedSize(true);
        rvBrowseAllPokemonLM = new LinearLayoutManager(context);
        rvBrowseAllPokemon.setLayoutManager(rvBrowseAllPokemonLM);
        rvBrowseAllPokemonAdapter = new PokemonListRVAdapter(mainPokemonDetailsList);
        rvBrowseAllPokemon.setAdapter(rvBrowseAllPokemonAdapter);

        rvBrowseAllPokemon.addOnScrollListener(pokemonListScrollListener);

        offset = 20;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POKEAPI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fragAllPokeApi = retrofit.create(PokeApi.class);

        fade = new Fade();
        decor = getActivity().getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getActivity().getWindow().setEnterTransition(fade);
        getActivity().getWindow().setExitTransition(fade);

        favPokemonViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(FavPokemonViewModel.class);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                favPokemonViewModel.insert(new FavPokemon(rvBrowseAllPokemonAdapter.getPokemonAt(viewHolder.getAdapterPosition()).getName()
                        , rvBrowseAllPokemonAdapter.getPokemonAt(viewHolder.getAdapterPosition()).getId()));
                Toast.makeText(context, "Added Pokemon to Favourites", Toast.LENGTH_SHORT).show();
                rvBrowseAllPokemonAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rvBrowseAllPokemon);

        callFirstPage();

        return fragView;

    }

    private void callFirstPage() {
        Call<PokemonListData> firstPokemonListDataCall = fragAllPokeApi.getFirstPokemonList();

        firstPokemonListDataCall.enqueue(new Callback<PokemonListData>() {
            @Override
            public void onResponse(Call<PokemonListData> call, Response<PokemonListData> response) {
                if (!response.isSuccessful()) {
                    Log.d("GetPokemonList", "onResponse: Code:" + response.code());
                    return;
                }
                currentPokemonListData = response.body();
                mainPokemonDetailsList.addAll(currentPokemonListData.getPokemonListDetails());
                setRVAdapter(mainPokemonDetailsList);
                //Log.d("PokemonDetailList", " Size: " + pokemonDetailList.size());
            }

            @Override
            public void onFailure(Call<PokemonListData> call, Throwable t) {
                Log.e("GetPokemonList", "onFailure: " + t.getMessage());
            }
        });
    }

    RecyclerView.OnScrollListener pokemonListScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (isLastPokemon(recyclerView)) {
                Log.i(TAG, "onScrolled: last pokemon");
                if (currentPokemonListData.getNext() != null) {
                    Log.i(TAG, "onScrolled: Next " + currentPokemonListData.getNext());
                    Call<PokemonListData> nextPokemonListDataCall = fragAllPokeApi.getNextPage(offset);

                    nextPokemonListDataCall.enqueue(new Callback<PokemonListData>() {
                        @Override
                        public void onResponse(Call<PokemonListData> call, Response<PokemonListData> response) {
                            if (!response.isSuccessful()) {
                                Log.d("GetPokemonList", "onResponse: Code:" + response.code());
                                return;
                            }
                            currentPokemonListData = response.body();
                            mainPokemonDetailsList.addAll(currentPokemonListData.getPokemonListDetails());
                            offset+=20;
                            Log.d(TAG, "onResponse: new list size" + mainPokemonDetailsList.size());
                            setRVAdapter(mainPokemonDetailsList);
                        }

                        @Override
                        public void onFailure(Call<PokemonListData> call, Throwable t) {
                            Log.e("GetPokemonList", "onFailure: " + t.getMessage());
                        }
                    });
                } else {
                    assert true;
                }
            }
        }
    };

    private boolean isLastPokemon(RecyclerView rv) {
        if (rv.getAdapter().getItemCount() != 0) {

            int lastVisiblePokemonPosition = ((LinearLayoutManager) rv.getLayoutManager()).findLastCompletelyVisibleItemPosition();

            if (lastVisiblePokemonPosition != RecyclerView.NO_POSITION && lastVisiblePokemonPosition == rv.getAdapter().getItemCount()-1) {
                return true;
            }

        }

        return false;
    }

    public void setRVAdapter(ArrayList<PokemonListDetail> pokemonDetailList) {
        Log.d(TAG, "setRVAdapter: " + pokemonDetailList.size());
        rvBrowseAllPokemonAdapter.notifyDataSetChanged();
        rvBrowseAllPokemonAdapter.setOnPokemonClickListener(new PokemonListRVAdapter.OnPokemonClickListener() {
            @Override
            public void OnPokemonClick(int position, ImageView sharedImageView) {
                PokemonListDetail selectedPokemon = mainPokemonDetailsList.get(position);
                String pokemonName = selectedPokemon.getName();
                //Toast.makeText(context, pokemonName, Toast.LENGTH_SHORT).show();
                Intent pokemonDetail = new Intent(getActivity(), PokemonDetailActivity.class);
                pokemonDetail.putExtra("POKEMON_ID", selectedPokemon.getId());
                pokemonDetail.putExtra("POKEMON_NAME", selectedPokemon.getName());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        sharedImageView,
                        ViewCompat.getTransitionName(sharedImageView));

                startActivity(pokemonDetail, options.toBundle());
            }
        });
    }

}

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex_trial1.DataClasses.PokemonItemsListData;
import com.example.pokedex_trial1.DataClasses.PokemonItemsListDetail;
import com.example.pokedex_trial1.DataClasses.PokemonListData;
import com.example.pokedex_trial1.ItemDetailActivity;
import com.example.pokedex_trial1.PokeApi;
import com.example.pokedex_trial1.R;
import com.example.pokedex_trial1.RVAdapters.PokemonItemsRVAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentBrowseItems extends Fragment {
    View fragView;
    Context context;

    public FragmentBrowseItems(Context context) {
        this.context = context;
    }

    private static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";
    private static final String TAG = "FragAllItems";

    RecyclerView rvBrowseItems;
    RecyclerView.LayoutManager rvBrowseItemsLM;
    PokemonItemsRVAdapter rvBrowseItemsAdapter;

    ArrayList<PokemonItemsListDetail> mainPokemonItemsList;

    PokeApi fragItemsPokeApi;

    PokemonItemsListData currentPokemonItemsData;

    int offset;

    Fade fade;
    View decor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Browse Pokemon Items");
        fragView = inflater.inflate(R.layout.fragment_browse_items, container, false);

        mainPokemonItemsList = new ArrayList<>();

        rvBrowseItems = fragView.findViewById(R.id.rvBrowseitems);
        rvBrowseItems.setHasFixedSize(true);
        rvBrowseItemsLM = new LinearLayoutManager(context);
        rvBrowseItems.setLayoutManager(rvBrowseItemsLM);
        rvBrowseItemsAdapter = new PokemonItemsRVAdapter(mainPokemonItemsList);
        rvBrowseItems.setAdapter(rvBrowseItemsAdapter);

        rvBrowseItems.addOnScrollListener(pokemonItemsScrollListener);

        offset = 20;

        fade = new Fade();
        decor = getActivity().getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getActivity().getWindow().setEnterTransition(fade);
        getActivity().getWindow().setExitTransition(fade);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POKEAPI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fragItemsPokeApi = retrofit.create(PokeApi.class);

        callFirstItemsPage();

        return fragView;
    }

    private void callFirstItemsPage() {

        Call<PokemonItemsListData> firstPokemonItemsCall = fragItemsPokeApi.getFirstItemsList();

        firstPokemonItemsCall.enqueue(new Callback<PokemonItemsListData>() {
            @Override
            public void onResponse(Call<PokemonItemsListData> call, Response<PokemonItemsListData> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Code:" + response.code());
                    return;
                }

                currentPokemonItemsData = response.body();
                mainPokemonItemsList.addAll(currentPokemonItemsData.getPokemonItemsDetails());
                updateRVAdapter(mainPokemonItemsList);
            }

            @Override
            public void onFailure(Call<PokemonItemsListData> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    RecyclerView.OnScrollListener pokemonItemsScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (isLastItem(recyclerView)) {
                Log.i(TAG, "onScrolled: LastItem");
                if (currentPokemonItemsData.getNext() != null) {
                    Log.i(TAG, "onScrolled: Next " + currentPokemonItemsData.getNext());
                    Call<PokemonItemsListData> nextPokemonListDataCall = fragItemsPokeApi.getNextItemsPage(offset);

                    nextPokemonListDataCall.enqueue(new Callback<PokemonItemsListData>() {
                        @Override
                        public void onResponse(Call<PokemonItemsListData> call, Response<PokemonItemsListData> response) {
                            if (!response.isSuccessful()) {
                                Log.d(TAG, "onResponse: Code:" + response.code());
                                return;
                            }
                            currentPokemonItemsData = response.body();
                            mainPokemonItemsList.addAll(currentPokemonItemsData.getPokemonItemsDetails());
                            offset += 20;
                            Log.d(TAG, "onResponse: new list size" + mainPokemonItemsList.size());
                            updateRVAdapter(mainPokemonItemsList);
                        }

                        @Override
                        public void onFailure(Call<PokemonItemsListData> call, Throwable t) {
                            Log.e("GetPokemonList", "onFailure: " + t.getMessage());
                        }
                    });
                } else {
                    assert true;
                }
            }
        }
    };

    private boolean isLastItem(RecyclerView rv) {
        if (rv.getAdapter().getItemCount() != 0) {
            int lastVisiblePokemonPosition = ((LinearLayoutManager) rv.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisiblePokemonPosition != RecyclerView.NO_POSITION && lastVisiblePokemonPosition == rv.getAdapter().getItemCount() - 1) {
                return true;
            }
        }
        return false;
    }

    private void updateRVAdapter(ArrayList<PokemonItemsListDetail> mainPokemonItemsList) {
        Log.d(TAG, "setRVAdapter: " + mainPokemonItemsList.size());
        rvBrowseItemsAdapter.notifyDataSetChanged();
        rvBrowseItemsAdapter.setOnPokeItemClickListener(new PokemonItemsRVAdapter.OnPokeItemClickListener() {
            @Override
            public void OnPokeItemClick(int position, ImageView sharedImageView) {
                PokemonItemsListDetail selectedItem = mainPokemonItemsList.get(position);
                Intent itemDetail = new Intent(getActivity(), ItemDetailActivity.class);
                itemDetail.putExtra("ITEM_ID", selectedItem.getId());
                itemDetail.putExtra("ITEM_NAME", selectedItem.getItemName());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        sharedImageView,
                        ViewCompat.getTransitionName(sharedImageView));
                startActivity(itemDetail, options.toBundle());
            }
        });
    }
}

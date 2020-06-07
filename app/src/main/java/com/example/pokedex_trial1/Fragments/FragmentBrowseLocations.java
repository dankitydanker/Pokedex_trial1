package com.example.pokedex_trial1.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex_trial1.LocationDetailActivity;
import com.example.pokedex_trial1.MiscStuffClasses.LocationListData;
import com.example.pokedex_trial1.MiscStuffClasses.LocationListDetail;
import com.example.pokedex_trial1.PokeApi;
import com.example.pokedex_trial1.R;
import com.example.pokedex_trial1.RVAdapters.PokemonLocationsRVAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentBrowseLocations extends Fragment {

    View fragView;
    Context context;

    public FragmentBrowseLocations(Context context) {
        this.context = context;
    }

    private static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";
    private static final String TAG = "FragLocations";

    PokeApi locationsPokeApi;

    ArrayList<LocationListDetail> mainLocationsList;
    LocationListData currentLocationsList;

    int offset;

    RecyclerView rvBrowseLocations;
    RecyclerView.LayoutManager rvBrowseLocationsLM;
    PokemonLocationsRVAdapter rvBrowseLocationsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_browse_locations, container, false);
        getActivity().setTitle("Browse Locations");

        mainLocationsList = new ArrayList<>();
        offset = 20;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POKEAPI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        locationsPokeApi = retrofit.create(PokeApi.class);

        rvBrowseLocations = fragView.findViewById(R.id.rvBrowseLocations);
        rvBrowseLocations.setHasFixedSize(true);
        rvBrowseLocationsLM = new LinearLayoutManager(context);
        rvBrowseLocations.setLayoutManager(rvBrowseLocationsLM);
        rvBrowseLocationsAdapter = new PokemonLocationsRVAdapter(mainLocationsList);
        rvBrowseLocations.setAdapter(rvBrowseLocationsAdapter);

        rvBrowseLocations.addOnScrollListener(locationsOnScrollListener);

        callFirstPage();

        return fragView;
    }

    private void callFirstPage() {

        Call<LocationListData> firstDataCall = locationsPokeApi.getFirstLocationList();

        firstDataCall.enqueue(new Callback<LocationListData>() {
            @Override
            public void onResponse(Call<LocationListData> call, Response<LocationListData> response) {
                if (!response.isSuccessful()) {
                    Log.d("GetPokemonList", "onResponse: Code:" + response.code());
                    return;
                }
                currentLocationsList = response.body();
                mainLocationsList.addAll(currentLocationsList.getResults());
                setRVAdapter(mainLocationsList);
            }

            @Override
            public void onFailure(Call<LocationListData> call, Throwable t) {
                Log.e("GetPokemonList", "onFailure: " + t.getMessage());
            }
        });

    }

    private void setRVAdapter(ArrayList<LocationListDetail> mainLocationsList) {
        rvBrowseLocationsAdapter.notifyDataSetChanged();
        rvBrowseLocationsAdapter.setOnLocClickListener(new PokemonLocationsRVAdapter.OnLocClickListener() {
            @Override
            public void OnLocClick(int position) {
                LocationListDetail selectedLocation = mainLocationsList.get(position);
                Intent locDetail = new Intent(getActivity(), LocationDetailActivity.class);
                locDetail.putExtra("LOCATION_ID", selectedLocation.getLocationId());
                startActivity(locDetail);
            }
        });
    }

    RecyclerView.OnScrollListener locationsOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (isLastLocation(recyclerView)) {
                Log.i(TAG, "onScrolled: last location");
                if (currentLocationsList.getNext() != null) {
                    Log.i(TAG, "onScrolled: Next " + currentLocationsList.getNext());
                    Call<LocationListData> nextPokemonListDataCall = locationsPokeApi.getNextLocationPage(offset);

                    nextPokemonListDataCall.enqueue(new Callback<LocationListData>() {
                        @Override
                        public void onResponse(Call<LocationListData> call, Response<LocationListData> response) {
                            if (!response.isSuccessful()) {
                                Log.d(TAG, "onResponse: Code:" + response.code());
                                return;
                            }
                            currentLocationsList = response.body();
                            mainLocationsList.addAll(currentLocationsList.getResults());
                            offset+=20;
                            Log.d(TAG, "onResponse: new list size" + mainLocationsList.size());
                            setRVAdapter(mainLocationsList);
                        }

                        @Override
                        public void onFailure(Call<LocationListData> call, Throwable t) {
                            Log.e("GetPokemonList", "onFailure: " + t.getMessage());
                        }
                    });
                } else {
                    assert true;
                }
            }

        }
    };

    public boolean isLastLocation(RecyclerView rv) {
        if (rv.getAdapter().getItemCount() != 0) {
            int lastVisibleLocationPosition = ((LinearLayoutManager) rv.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleLocationPosition != RecyclerView.NO_POSITION && lastVisibleLocationPosition == rv.getAdapter().getItemCount()-1) {
                return true;
            }
        }
        return false;
    }
}

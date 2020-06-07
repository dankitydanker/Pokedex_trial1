package com.example.pokedex_trial1.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pokedex_trial1.MiscStuffClasses.LocationAreas;
import com.example.pokedex_trial1.MiscStuffClasses.LocationDetail;
import com.example.pokedex_trial1.PokeApi;
import com.example.pokedex_trial1.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentSearchLocation extends Fragment {
    View fragView;
    Context context;
    String locId;

    private static final String TAG = "GetLocDetail";
    public static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";

    TextView tvLocName, tvLocRegion, tvLocAreas;

    PokeApi locPokeApi;

    LocationDetail searchLocation;

    public FragmentSearchLocation(Context context, String locId) {
        this.context = context;
        this.locId = locId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_search_location, container, false);

        tvLocName = fragView.findViewById(R.id.tvLocName);
        tvLocRegion = fragView.findViewById(R.id.tvLocRegion);
        tvLocAreas = fragView.findViewById(R.id.tvLocAreas);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POKEAPI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        locPokeApi = retrofit.create(PokeApi.class);

        getLocDetails();

        return fragView;
    }

    private void getLocDetails() {

        Call<LocationDetail> locationDetailCall = locPokeApi.getLocationDetail(locId);

        locationDetailCall.enqueue(new Callback<LocationDetail>() {
            @Override
            public void onResponse(Call<LocationDetail> call, Response<LocationDetail> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Code:" + response.code());
                    return;
                }
                searchLocation = response.body();
                fillLocDetails();
            }

            @Override
            public void onFailure(Call<LocationDetail> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void fillLocDetails() {

        tvLocName.setText(searchLocation.getName());
        tvLocRegion.setText(searchLocation.getRegion().getRegionName());

        for (LocationAreas areas : searchLocation.getAreas()) {
            tvLocAreas.append(areas.getAreaName().toUpperCase() + "\n");
        }

    }
}

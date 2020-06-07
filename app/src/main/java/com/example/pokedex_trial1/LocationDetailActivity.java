package com.example.pokedex_trial1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.pokedex_trial1.MiscStuffClasses.LocationAreas;
import com.example.pokedex_trial1.MiscStuffClasses.LocationDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationDetailActivity extends AppCompatActivity {

    String locId;

    private static final String TAG = "GetLocDetail";
    public static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";

    TextView tvLocName, tvLocRegion, tvLocAreas;

    PokeApi locPokeApi;

    LocationDetail searchLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        locId = getIntent().getStringExtra("LOCATION_ID");

        tvLocName = findViewById(R.id.tvLocName);
        tvLocRegion = findViewById(R.id.tvLocRegion);
        tvLocAreas = findViewById(R.id.tvLocAreas);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POKEAPI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        locPokeApi = retrofit.create(PokeApi.class);

        getLocDetails();
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

package com.example.pokedex_trial1.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.example.pokedex_trial1.DataClasses.PokemonListDetail;
import com.example.pokedex_trial1.DataClasses.PokemonTypeData;
import com.example.pokedex_trial1.DataClasses.PokemonTypeList;
import com.example.pokedex_trial1.PokeApi;
import com.example.pokedex_trial1.RVAdapters.PokemonListRVAdapter;
import com.example.pokedex_trial1.Room.FavPokemon;
import com.example.pokedex_trial1.Room.FavPokemonViewModel;
import com.example.pokedex_trial1.Spinner.PokemonTypeSpinnerAdapter;
import com.example.pokedex_trial1.Spinner.PokemonTypeSpinnerItem;
import com.example.pokedex_trial1.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentBrowseByType extends Fragment {
    View fragView;
    Context context;

    private FavPokemonViewModel favPokemonViewModel;

    private static final String TAG = "FragBrowseType";
    private static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";

    RecyclerView rvBrowseType;
    RecyclerView.LayoutManager rvBrowseTypeLM;
    PokemonListRVAdapter rvBrowseTypeAdapter;

    ArrayList<PokemonListDetail> typePokemonList;

    PokeApi fragTypePokeApi;

    PokemonTypeData currentPokemonTypeData;

    public FragmentBrowseByType(Context context) {
        this.context = context;
    }

    ArrayList<PokemonTypeSpinnerItem> pokemonTypes;
    PokemonTypeSpinnerAdapter pokemonTypeAdapter;
    Spinner spPokemonType;

    TextView tvFragCurrentType;

    Fade fade;
    View decor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Browse by Type");
        fragView = inflater.inflate(R.layout.fragment_browse_type, container, false);

        typePokemonList = new ArrayList<>();

        initTypeList();

        fade = new Fade();
        decor = getActivity().getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getActivity().getWindow().setEnterTransition(fade);
        getActivity().getWindow().setExitTransition(fade);

        rvBrowseType = fragView.findViewById(R.id.rvBrowseType);
        rvBrowseType.setHasFixedSize(true);
        rvBrowseTypeLM = new LinearLayoutManager(context);
        rvBrowseType.setLayoutManager(rvBrowseTypeLM);
        rvBrowseTypeAdapter = new PokemonListRVAdapter(typePokemonList);
        rvBrowseType.setAdapter(rvBrowseTypeAdapter);

        tvFragCurrentType = fragView.findViewById(R.id.tvFragCurrentType);
        spPokemonType = fragView.findViewById(R.id.spPokemonType);
        pokemonTypeAdapter = new PokemonTypeSpinnerAdapter(context, pokemonTypes);
        spPokemonType.setAdapter(pokemonTypeAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POKEAPI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fragTypePokeApi = retrofit.create(PokeApi.class);

        spPokemonType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PokemonTypeSpinnerItem selectedItem = (PokemonTypeSpinnerItem) adapterView.getItemAtPosition(i);
                Log.i(TAG, "onItemSelected: " + i);
                String selectedType = selectedItem.getTypeName();
                tvFragCurrentType.setText(selectedType+" type Pokemon");
                Toast.makeText(context, selectedType, Toast.LENGTH_SHORT).show();
                callPokemonType(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        favPokemonViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(FavPokemonViewModel.class);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                favPokemonViewModel.insert(new FavPokemon(rvBrowseTypeAdapter.getPokemonAt(viewHolder.getAdapterPosition()).getName()
                        , rvBrowseTypeAdapter.getPokemonAt(viewHolder.getAdapterPosition()).getId()));
                Toast.makeText(context, "Added Pokemon to Favourites", Toast.LENGTH_SHORT).show();
                rvBrowseTypeAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rvBrowseType);

        return fragView;
    }

    private void callPokemonType(int typeId) {

        typePokemonList = new ArrayList<>();

        Call<PokemonTypeData> pokemonTypeDataCall = fragTypePokeApi.getPokemonType(typeId);

        pokemonTypeDataCall.enqueue(new Callback<PokemonTypeData>() {
            @Override
            public void onResponse(Call<PokemonTypeData> call, Response<PokemonTypeData> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Code:" + response.code());
                    return;
                }

                currentPokemonTypeData = response.body();
                for (PokemonTypeList eachPokemonType: currentPokemonTypeData.getPokemonTypeList()) {
                    PokemonListDetail pokemonOfSelectedType = eachPokemonType.getPokemon();
                    typePokemonList.add(pokemonOfSelectedType);
                }
                tvFragCurrentType.append(" ("+typePokemonList.size()+") : ");
                setRVAdapter(typePokemonList);

            }

            @Override
            public void onFailure(Call<PokemonTypeData> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void setRVAdapter(ArrayList<PokemonListDetail> selectedTypePokemon) {
        Log.i(TAG, "setRVAdapter: Total Pokemon of this type: " + typePokemonList.size());
        rvBrowseTypeAdapter = new PokemonListRVAdapter(selectedTypePokemon);
        rvBrowseType.setAdapter(rvBrowseTypeAdapter);
        rvBrowseTypeAdapter.setOnPokemonClickListener(new PokemonListRVAdapter.OnPokemonClickListener() {
            @Override
            public void OnPokemonClick(int position, ImageView sharedImageView) {
                PokemonListDetail selectedPokemon = typePokemonList.get(position);
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

    private void initTypeList() {
        pokemonTypes = new ArrayList<>();
        pokemonTypes.add(new PokemonTypeSpinnerItem("Normal", R.drawable.normal_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Fighting", R.drawable.fighting_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Flying", R.drawable.flying_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Poison", R.drawable.poison_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Ground", R.drawable.ground_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Rock", R.drawable.rock_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Bug", R.drawable.bug_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Ghost", R.drawable.ghost_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Steel", R.drawable.steel_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Fire", R.drawable.fire_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Water", R.drawable.water_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Grass", R.drawable.grass_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Electric", R.drawable.electric_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Psychic", R.drawable.psychic_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Ice", R.drawable.ice_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Dragon", R.drawable.dragon_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Dark", R.drawable.dark_type));
        pokemonTypes.add(new PokemonTypeSpinnerItem("Fairy", R.drawable.fairy_type));
    }
}

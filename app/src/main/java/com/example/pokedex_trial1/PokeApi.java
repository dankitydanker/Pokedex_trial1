package com.example.pokedex_trial1;

import androidx.cardview.widget.CardView;

import com.example.pokedex_trial1.DataClasses.PokemonItemsListData;
import com.example.pokedex_trial1.DataClasses.PokemonListData;
import com.example.pokedex_trial1.DataClasses.PokemonTypeData;
import com.example.pokedex_trial1.MiscStuffClasses.LocationDetail;
import com.example.pokedex_trial1.MiscStuffClasses.LocationListData;
import com.example.pokedex_trial1.MiscStuffClasses.PokeItemDetail;
import com.example.pokedex_trial1.Pokemon.EvolutionChainMain;
import com.example.pokedex_trial1.Pokemon.PokemonDetail;
import com.example.pokedex_trial1.Pokemon.PokemonSpecies_Detailed;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApi {

    @GET("pokemon")
    Call<PokemonListData> getFirstPokemonList();

    @GET("pokemon")
    Call<PokemonListData> getNextPage(@Query("offset") int next);

    @GET("type/{type_id}")
    Call<PokemonTypeData> getPokemonType(@Path("type_id") int typeId);

    @GET("item")
    Call<PokemonItemsListData> getFirstItemsList();

    @GET("item")
    Call<PokemonItemsListData> getNextItemsPage(@Query("offset") int next);

    @GET("pokemon/{id}")
    Call<PokemonDetail> getPokemonDetail(@Path("id") String pokemonId);

    @GET("pokemon-species/{id}")
    Call<PokemonSpecies_Detailed> getPokemonSpecies(@Path("id") String pokemonId);

    @GET("evolution-chain/{evoChainId}")
    Call<EvolutionChainMain> getPokemonEvoChain(@Path("evoChainId") String evoChainId);

    @GET("item/{itemId}")
    Call<PokeItemDetail> getPokeItemDetail(@Path("itemId") String itemId);

    @GET("location")
    Call<LocationListData> getFirstLocationList();

    @GET("location")
    Call<LocationListData> getNextLocationPage(@Query("offset") int next);

    @GET("location/{locId}")
    Call<LocationDetail> getLocationDetail(@Path("locId") String locId);
}

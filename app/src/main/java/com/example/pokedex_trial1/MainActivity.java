package com.example.pokedex_trial1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void browsePokemon(View view) {
        startActivity(new Intent(MainActivity.this, BrowsePokemon.class));
    }

    public void searchThings(View view) {
        startActivity(new Intent(MainActivity.this, SearchActivity.class));
    }

    public void showFavPokemon(View view) {
        startActivity(new Intent(MainActivity.this, FavPokemonActivity.class));
    }
}

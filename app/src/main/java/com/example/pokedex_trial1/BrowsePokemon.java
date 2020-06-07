package com.example.pokedex_trial1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.pokedex_trial1.Fragments.FragmentBrowseAllPokemon;
import com.example.pokedex_trial1.Fragments.FragmentBrowseByType;
import com.example.pokedex_trial1.Fragments.FragmentBrowseItems;
import com.example.pokedex_trial1.Fragments.FragmentBrowseLocations;
import com.google.android.material.navigation.NavigationView;

public class BrowsePokemon extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dlBrowsePokemon;

    Toolbar tbBrowsePokemon;

    NavigationView navViewBrowsePokemon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_pokemon);

        tbBrowsePokemon = findViewById(R.id.tbBrowsePokemon);
        setSupportActionBar(tbBrowsePokemon);
        setTitle("Browse");

        dlBrowsePokemon = findViewById(R.id.dlBrowsePokemon);
        navViewBrowsePokemon = findViewById(R.id.navViewBrowsePokemon);
        navViewBrowsePokemon.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dlBrowsePokemon, tbBrowsePokemon, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dlBrowsePokemon.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, new FragmentBrowseAllPokemon(getApplicationContext())).commit();
            navViewBrowsePokemon.setCheckedItem(R.id.browse_all);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.browse_all:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, new FragmentBrowseAllPokemon(getApplicationContext())).commit();
                break;
            case R.id.browse_type:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, new FragmentBrowseByType(getApplicationContext())).commit();
                break;
            case R.id.browse_items:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, new FragmentBrowseItems(getApplicationContext())).commit();
                break;
            case R.id.browse_locations:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, new FragmentBrowseLocations(getApplicationContext())).commit();
                break;
        }

        dlBrowsePokemon.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (dlBrowsePokemon.isDrawerOpen(GravityCompat.START)) {
            dlBrowsePokemon.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

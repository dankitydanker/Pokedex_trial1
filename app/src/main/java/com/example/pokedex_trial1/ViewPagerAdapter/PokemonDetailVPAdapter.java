package com.example.pokedex_trial1.ViewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PokemonDetailVPAdapter extends FragmentPagerAdapter {

    private final List<Fragment> pokemonDetailFragments = new ArrayList<>();
    private final List<String> pokemonDetailFragmentTitles = new ArrayList<>();

    public PokemonDetailVPAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return pokemonDetailFragments.get(position);
    }


    @Override
    public int getCount() {
        return pokemonDetailFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pokemonDetailFragmentTitles.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        pokemonDetailFragments.add(fragment);
        pokemonDetailFragmentTitles.add(title);
    }

}

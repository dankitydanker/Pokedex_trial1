package com.example.pokedex_trial1.Pokemon;

import com.google.gson.annotations.SerializedName;

public class PokemonAbilities_Broad {
    @SerializedName("ability")
    private PokemonAbility_Detailed pokemonAbility_detailed;
    @SerializedName("is_hidden")
    private boolean isAbilityHidden;
    @SerializedName("slot")
    private int abilitySlot;

    public PokemonAbility_Detailed getPokemonAbility_detailed() {
        return pokemonAbility_detailed;
    }

    public void setPokemonAbility_detailed(PokemonAbility_Detailed pokemonAbility_detailed) {
        this.pokemonAbility_detailed = pokemonAbility_detailed;
    }

    public boolean isAbilityHidden() {
        return isAbilityHidden;
    }

    public void setAbilityHidden(boolean abilityHidden) {
        isAbilityHidden = abilityHidden;
    }

    public int getAbilitySlot() {
        return abilitySlot;
    }

    public void setAbilitySlot(int abilitySlot) {
        this.abilitySlot = abilitySlot;
    }
}

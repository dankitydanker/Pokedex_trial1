package com.example.pokedex_trial1.MiscStuffClasses;

import com.google.gson.annotations.SerializedName;

public class EffectEntries {
    @SerializedName("effect")
    private String effect;

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}

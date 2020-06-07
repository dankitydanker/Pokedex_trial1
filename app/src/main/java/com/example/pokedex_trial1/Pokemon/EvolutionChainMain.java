package com.example.pokedex_trial1.Pokemon;

import com.google.gson.annotations.SerializedName;

public class EvolutionChainMain {
    @SerializedName("id")
    private int evoChainId;
    @SerializedName("chain")
    private EvoChain evoChainBeginning;

    public int getEvoChainId() {
        return evoChainId;
    }

    public void setEvoChainId(int evoChainId) {
        this.evoChainId = evoChainId;
    }

    public EvoChain getEvoChainBeginning() {
        return evoChainBeginning;
    }

    public void setEvoChainBeginning(EvoChain evoChainBeginning) {
        this.evoChainBeginning = evoChainBeginning;
    }
}

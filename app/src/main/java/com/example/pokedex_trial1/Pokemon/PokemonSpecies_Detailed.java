package com.example.pokedex_trial1.Pokemon;

import com.google.gson.annotations.SerializedName;

public class PokemonSpecies_Detailed {

    @SerializedName("evolution_chain")
    private evoChainClass evolution_chain;

    public evoChainClass getEvolution_chain() {
        return evolution_chain;
    }

    public void setEvolution_chain(evoChainClass evolution_chain) {
        this.evolution_chain = evolution_chain;
    }

    public class evoChainClass {
        @SerializedName("url")
        private String evoChainURL;

        private transient String evoChainId;

        public String getEvoChainURL() {
            return evoChainURL;
        }

        public void setEvoChainURL(String evoChainURL) {
            this.evoChainURL = evoChainURL;
        }

        public String getEvoChainId() {
            // https://pokeapi.co/api/v2/evolution-chain/10/
            // 41 is / before evoId
            return evoChainURL.substring(42, evoChainURL.length() - 1);
        }

        public void setEvoChainId(String evoChainId) {
            this.evoChainId = evoChainURL.substring(42, evoChainURL.length() - 1);
        }
    }

}

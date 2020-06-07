package com.example.pokedex_trial1.Pokemon;

import com.google.gson.annotations.SerializedName;

public class PokemonType_Detailed {
    @SerializedName("name")
    private String typeName;
    @SerializedName("url")
    private String typeURL;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeURL() {
        return typeURL;
    }

    public void setTypeURL(String typeURL) {
        this.typeURL = typeURL;
    }
}

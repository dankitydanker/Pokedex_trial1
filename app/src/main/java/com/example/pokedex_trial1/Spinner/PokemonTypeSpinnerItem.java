package com.example.pokedex_trial1.Spinner;

public class PokemonTypeSpinnerItem {
    private String typeName;
    private int typeImage;

    public PokemonTypeSpinnerItem(String typeName, int typeImage) {
        this.typeName = typeName;
        this.typeImage = typeImage;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeImage() {
        return typeImage;
    }

    public void setTypeImage(int typeImage) {
        this.typeImage = typeImage;
    }
}

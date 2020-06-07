package com.example.pokedex_trial1.DataClasses;

public class PokemonListDetail {

    private String name;

    private String url;

    private transient String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        // 33 is / before id
        //return "https://pokeapi.co/api/v2/pokemon/16/";
        return url.substring(34,url.length()-1);
    }

    public void setId(String id) {
        this.id = url.substring(34,url.length()-1);
    }

}

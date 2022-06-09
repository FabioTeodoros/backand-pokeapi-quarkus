package com.pokeapi.domain.entities;

public class PokemonResponse {

    private String name;
    private String url;

    public PokemonResponse() {
        this.name = null;
        this.url = null;
    }

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
}

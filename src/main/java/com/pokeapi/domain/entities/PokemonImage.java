package com.pokeapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PokemonImage {

    private String spriteFrontDefault;
    private PokemonBigImage pokemonBigImage;

    @JsonProperty("other")
    public PokemonBigImage getSpriteDreamWorld() {
        return pokemonBigImage;
    }

    public void setSpriteDreamWorld(PokemonBigImage pokemonBigImage) {
        this.pokemonBigImage = pokemonBigImage;
    }

    @JsonProperty("front_default")
    public String getSpriteFrontDefault() {
        return spriteFrontDefault;
    }

    public void setSpriteFrontDefault(String spriteFrontDefault) {
        this.spriteFrontDefault = spriteFrontDefault;
    }

}


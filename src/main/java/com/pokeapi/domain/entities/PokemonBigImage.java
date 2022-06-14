package com.pokeapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PokemonBigImage {

    private PokemonSmallImage pokemonSmallImage;

    @JsonProperty("dream_world")
    public PokemonSmallImage getFrontDefault() {
        return pokemonSmallImage;
    }

    public void setFrontDefault(PokemonSmallImage pokemonSmallImage) {
        this.pokemonSmallImage = pokemonSmallImage;
    }
}

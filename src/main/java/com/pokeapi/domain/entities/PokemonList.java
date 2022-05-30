package com.pokeapi.domain.entities;

import java.util.List;

public class PokemonList {

    List<PokemonForList> results;

    public List<PokemonForList> getResults() {
        return results;
    }

    public void setResults(List<PokemonForList> results) {
        this.results = results;
    }
}

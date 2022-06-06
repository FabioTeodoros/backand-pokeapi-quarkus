package com.pokeapi.domain.entities;

import java.util.List;

public class PokemonList {

    private String count;
    private List<PokemonForList> results;

    public PokemonList() {
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<PokemonForList> getResults() {
        return results;
    }

    public void setResults(List<PokemonForList> results) {
        this.results = results;
    }
}

package com.pokeapi.domain.entities;

import java.util.List;

public class PokemonList {

    private String count;
    List<PokemonForList> results;


    public List<PokemonForList> getResults() {
        return results;
    }

    public void setResults(List<PokemonForList> results) {
        this.results = results;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}

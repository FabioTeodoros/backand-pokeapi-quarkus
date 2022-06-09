package com.pokeapi.domain.entities;

import java.util.List;

public class PokemonFullList {

    private Integer count;
    private List<PokemonFull> results;

    public PokemonFullList() {
        this.count = null;
        this.results = null;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<PokemonFull> getResults() {
        return results;
    }

    public void setResults(List<PokemonFull> results) {
        this.results = results;
    }
}

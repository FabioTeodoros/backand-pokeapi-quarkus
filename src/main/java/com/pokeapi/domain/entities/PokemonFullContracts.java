package com.pokeapi.domain.entities;

import java.util.List;

public class PokemonFullContracts {

    private Integer count;
    private List<PokemonFullContract> results;

    public PokemonFullContracts() {

    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<PokemonFullContract> getResults() {
        return results;
    }

    public void setResults(List<PokemonFullContract> results) {
        this.results = results;
    }
}

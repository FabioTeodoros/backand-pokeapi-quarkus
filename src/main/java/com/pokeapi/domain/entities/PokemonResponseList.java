package com.pokeapi.domain.entities;

import java.util.List;

public class PokemonResponseList {

    private String count;
    private List<PokemonResponse> results;

    public PokemonResponseList() {
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<PokemonResponse> getResults() {
        return results;
    }

    public void setResults(List<PokemonResponse>results) {
        this.results = results;
    }
}

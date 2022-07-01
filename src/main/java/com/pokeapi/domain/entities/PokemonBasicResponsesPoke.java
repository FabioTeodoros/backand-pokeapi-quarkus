package com.pokeapi.domain.entities;

import java.util.List;

public class PokemonBasicResponsesPoke {

    private Integer count;
    private List<PokemonBasicResponse> results;

    public PokemonBasicResponsesPoke() {

    }

    public Integer getCount() {
        return count;
    }

    public List<PokemonBasicResponse> getResults() {
        return results;
    }

    public void setResults(List<PokemonBasicResponse>results) {
        this.results = results;
        this.count = results.size();
    }

}

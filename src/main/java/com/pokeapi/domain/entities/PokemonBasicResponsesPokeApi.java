package com.pokeapi.domain.entities;

import java.util.List;

public class PokemonBasicResponsesPokeApi {

    private Integer count;
    private List<PokemonBasicResponse> results;

    public PokemonBasicResponsesPokeApi() {

    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<PokemonBasicResponse> getResults() {
        return results;
    }

    public void setResults(List<PokemonBasicResponse>results) {
        this.results = results;
    }
}

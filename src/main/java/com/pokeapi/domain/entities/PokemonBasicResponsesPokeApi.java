package com.pokeapi.domain.entities;

import java.util.List;

public class PokemonBasicResponsesPokeApi {

    private Integer count;
    private List<PokemonBasicResponsePokeApi> results;

    public PokemonBasicResponsesPokeApi() {

    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<PokemonBasicResponsePokeApi> getResults() {
        return results;
    }

    public void setResults(List<PokemonBasicResponsePokeApi>results) {
        this.results = results;
    }
}

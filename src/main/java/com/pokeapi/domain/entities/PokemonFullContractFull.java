package com.pokeapi.domain.entities;

import java.util.List;

public class PokemonFullContractFull {

    private Integer count;
    private List<PokemonFullContractBasic> results;

    public PokemonFullContractFull() {

    }

    public Integer getCount() {
        return count;
    }

    public List<PokemonFullContractBasic> getResults() {
        return results;
    }

    public void setResults(List<PokemonFullContractBasic> results) {
        this.results = results;
        this.count = results.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PokemonFullContractFull)) return false;

        PokemonFullContractFull that = (PokemonFullContractFull) o;

        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        return results != null ? results.equals(that.results) : that.results == null;
    }

    @Override
    public int hashCode() {
        int result = count != null ? count.hashCode() : 0;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }
}

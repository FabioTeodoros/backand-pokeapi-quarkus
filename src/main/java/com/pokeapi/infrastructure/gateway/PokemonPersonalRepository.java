package com.pokeapi.infrastructure.gateway;

import com.pokeapi.domain.entities.PokemonDetail;

import java.util.List;

public interface PokemonPersonalRepository {
    PokemonDetail id(String id);
    List<PokemonDetail> list();
    void insert(PokemonDetail pokemonDetail);
    void delete(String id);
    void update(String id, PokemonDetail pokemonDetail);
}

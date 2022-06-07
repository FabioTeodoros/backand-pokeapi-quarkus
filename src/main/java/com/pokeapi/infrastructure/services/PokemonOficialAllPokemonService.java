package com.pokeapi.infrastructure.services;

import com.pokeapi.domain.entities.PokemonList;

public interface PokemonOficialAllPokemonService {

     PokemonList officialList(String model);
}

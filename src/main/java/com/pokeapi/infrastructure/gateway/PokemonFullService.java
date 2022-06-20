package com.pokeapi.infrastructure.gateway;

import com.pokeapi.domain.entities.PokemonFullContracts;

public interface PokemonFullService {
    PokemonFullContracts getPokemonOfficial();
    PokemonFullContracts getPokemonPersonal(String model);
    PokemonFullContracts getPokemonAll(String model);
    PokemonFullContracts pokemonGetValidation(String model);
}

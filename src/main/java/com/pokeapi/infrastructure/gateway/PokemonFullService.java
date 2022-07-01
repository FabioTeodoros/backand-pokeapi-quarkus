package com.pokeapi.infrastructure.gateway;

import com.pokeapi.domain.entities.PokemonFullContractFull;

public interface PokemonFullService {
    PokemonFullContractFull getPokemonOfficial();
    PokemonFullContractFull getPokemonPersonal();
    PokemonFullContractFull getPokemonAll();
    PokemonFullContractFull pokemonGetValidation(String model);
}

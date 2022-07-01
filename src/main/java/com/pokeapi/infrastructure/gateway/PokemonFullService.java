package com.pokeapi.infrastructure.gateway;

import com.pokeapi.domain.entities.PokemonFullContractList;

public interface PokemonFullService {
    PokemonFullContractList getPokemonOfficial();
    PokemonFullContractList getPokemonPersonal();
    PokemonFullContractList getPokemonAll();
    PokemonFullContractList pokemonGetValidation(String model);
}

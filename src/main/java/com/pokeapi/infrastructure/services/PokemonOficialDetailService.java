package com.pokeapi.infrastructure.services;

import com.pokeapi.domain.entities.PokemonDetail;

public interface PokemonOficialDetailService {

     PokemonDetail buscaPokemonDetail(String id);
}

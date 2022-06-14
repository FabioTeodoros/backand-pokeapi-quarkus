package com.pokeapi.infrastructure.gateway;

import com.pokeapi.domain.entities.PokemonDetail;

public interface PokemonOfficialDetailService {
     PokemonDetail buscaPokemonDetail(String id);
}

package com.pokeapi.infrastructure.gateway.config;

import com.pokeapi.infrastructure.imp.AbstractPokemonBasic;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PokemonBasicGateway extends AbstractPokemonBasic {
    public PokemonBasicGateway() {
        super("https://pokeapi.co/api/v2/pokemon?limit=2000&offset=0");
    }
}

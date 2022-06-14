package com.pokeapi.infrastructure.gateway.config;

import com.pokeapi.infrastructure.imp.AbstractPokemonDetail;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PokemonDetailGateway extends AbstractPokemonDetail {
    public PokemonDetailGateway() {
        super("https://pokeapi.co/api/v2/pokemon/");
    }
    @Override
    protected String buildPath(String id) {
        return String.format("%s", id);
    }
}

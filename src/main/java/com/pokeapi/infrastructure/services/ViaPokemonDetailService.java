package com.pokeapi.infrastructure.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;

@ApplicationScoped
public class ViaPokemonDetailService extends AbstractPokemonOficialDetailService {

    public ViaPokemonDetailService(){
        super("https://pokeapi.co/api/v2/pokemon/");
    }

    @Override
    protected String buildPath(String id) {
        return String.format("%s", id);
    }

}

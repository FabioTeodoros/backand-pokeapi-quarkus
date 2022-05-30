package com.pokeapi.infrastructure.services;

import javax.enterprise.context.Dependent;

@Dependent
public class ViaPokemonDetailService extends AbstractPokemonOficialDetailService {

    public ViaPokemonDetailService(){
        super("https://pokeapi.co/api/v2/pokemon/");
    }

    @Override
    protected String buildPath(Integer id) {
        return String.format("%s", id);
    }

}

package com.pokeapi.infrastructure.services;

import javax.enterprise.context.Dependent;

@Dependent
public class ViaPokemonListOficialListService extends AbstractOficialListService {

    public ViaPokemonListOficialListService(){
        super("https://pokeapi.co/api/v2/pokemon?limit=1126&offset=0");
    }

}

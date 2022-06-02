package com.pokeapi.infrastructure.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;

@Dependent
public class ViaPokemonAllPokemonServiceService extends AbstractPokemonOficialAllPokemonServiceService {

    public ViaPokemonAllPokemonServiceService(){
        super("https://pokeapi.co/api/v2/pokemon?limit=1126&offset=0");
    }

}

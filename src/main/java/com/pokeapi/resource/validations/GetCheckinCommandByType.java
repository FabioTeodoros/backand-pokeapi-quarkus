package com.pokeapi.resource.validations;

import com.pokeapi.infrastructure.services.PokemonOficialListService;
import com.pokeapi.infrastructure.services.PokemonPersonalService;
import com.pokeapi.resource.exceptions.InvalidRequestException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GetCheckinCommandByType {

    final static String PERSONAL = "personal";
    final static String OFFICIAL = "official";

    public GetCheckinCommandByType (
            PokemonOficialListService pokemonOficialListService,
            PokemonPersonalService pokemonPersonalService
    ){
        this.pokemonOficialListService = pokemonOficialListService;
        this.pokemonPersonalService = pokemonPersonalService;
    }
    @Inject
    PokemonOficialListService pokemonOficialListService;
    @Inject
    PokemonPersonalService pokemonPersonalService;

    public Object checkList(String model) {

        if((model).equals(OFFICIAL)) {
            return pokemonOficialListService.officialList();
        }
        else if((model).equals(PERSONAL)) {
            return pokemonPersonalService.pokemonListPersonal(model);
        }
        else {
            throw new InvalidRequestException("Error parameter invalid");
        }
    }
}

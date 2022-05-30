package com.pokeapi.resource;

import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.domain.entities.PokemonList;
import com.pokeapi.infrastructure.services.PokemonOficialAllPokemonService;
import com.pokeapi.infrastructure.services.PokemonOficialDetailService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/pokeoficial")
public class PokemonOficialResource {

    @Inject
    PokemonOficialDetailService pokemonOficialDetailService;
    @Inject
    PokemonOficialAllPokemonService pokemonOficialAllPokemonService;

    @GET
    @Path("/{id}")
    public PokemonDetail pokemonDetail(@PathParam("id")final Integer id) {
        return pokemonOficialDetailService.buscaPokemonDetail(id);
    }

    @GET
    @Path("/pokemon")
    public PokemonList getAll() {
        return pokemonOficialAllPokemonService.getAll();
    }

}

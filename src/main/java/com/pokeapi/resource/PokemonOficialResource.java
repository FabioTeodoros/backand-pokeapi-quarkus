package com.pokeapi.resource;

import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.infrastructure.services.PokemonOficialAllPokemonService;
import com.pokeapi.infrastructure.services.PokemonOficialDetailService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pokeoficial")
@Produces(MediaType.APPLICATION_JSON)

public class PokemonOficialResource {

    @Inject
    public PokemonOficialResource(
            final PokemonOficialDetailService pokemonOficialDetailService,
            final PokemonOficialAllPokemonService pokemonOficialAllPokemonService
    ){
        this.pokemonOficialDetailService = pokemonOficialDetailService;
        this.pokemonOficialAllPokemonService = pokemonOficialAllPokemonService;
    }

    @Inject
    PokemonOficialDetailService pokemonOficialDetailService;
    @Inject
    PokemonOficialAllPokemonService pokemonOficialAllPokemonService;

    @GET
    @Path("/todos/{id}")
    public Response pokemonDetail(@PathParam("id") final String id) {

       try{
           PokemonDetail result = pokemonOficialDetailService.buscaPokemonDetail(id);
           return Response.ok().entity(result).build();
       }
       catch (RuntimeException e){
           return Response.status(Response.Status.NO_CONTENT).build();
       }
    }

    @GET
    @Path("/todos")
    public Response getAll() {

        try{
            return Response.ok(pokemonOficialAllPokemonService.getAll()).build();
        }
        catch (RuntimeException e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }

    }

}

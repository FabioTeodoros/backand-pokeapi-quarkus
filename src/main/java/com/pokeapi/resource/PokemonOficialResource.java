package com.pokeapi.resource;

import com.pokeapi.domain.entities.PokemonList;
import com.pokeapi.infrastructure.services.PokemonOficialAllPokemonService;
import com.pokeapi.infrastructure.services.PokemonOficialDetailService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pokeoficial")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PokemonOficialResource {

    @Inject
    PokemonOficialDetailService pokemonOficialDetailService;
    @Inject
    PokemonOficialAllPokemonService pokemonOficialAllPokemonService;

    @GET
    @Path("/todos/{id}")
    public Response pokemonDetail(@PathParam("id") final Integer id) {

       try{
           return Response.ok(pokemonOficialDetailService.buscaPokemonDetail(id)).build();
       }
       catch (RuntimeException e){
           return Response.status(Response.Status.NOT_FOUND)
                   .entity("You entered an invalid ID.")
                   .build();
       }
    }

    @GET
    @Path("/todos")
    public Response getAll() {

        try{
            return Response.ok(pokemonOficialAllPokemonService.getAll()).build();
        }
        catch (RuntimeException e){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("You entered an invalid ID.")
                    .build();
        }

    }

}

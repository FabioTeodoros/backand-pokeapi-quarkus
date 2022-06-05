package com.pokeapi.resource;

import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.infrastructure.services.PokemonPersonalService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pokepersonal")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PokePersonalResource {

    @Inject
    public PokePersonalResource(PokemonPersonalService pokemonPersonalService){

        this.pokemonPersonalService = pokemonPersonalService;
    }

    @Inject
    PokemonPersonalService pokemonPersonalService;

    @GET
    @Path("/todos/{id}")
    public Response pokemonPersonalDetailId(PokemonDetail pokemonDetail, @PathParam("id") final String id) {

        try {
            return Response.ok(pokemonPersonalService.listDetailId(id)).build();
        }

        catch (RuntimeException e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @GET
    @Path("/todos")
    public Response listToPersonalBd() {

        try{
            return Response.ok(pokemonPersonalService.listPersonalBd()).build();
        }

        catch (RuntimeException e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @POST
    @Path("/create/{id}")
    public Response add(PokemonDetail pokemonDetail, @PathParam("id") final String id) {
        pokemonPersonalService.add(pokemonDetail, id);

        try{
            return Response.ok().build();
        }

        catch (RuntimeException e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteById(@PathParam("id") final String id)  {
        pokemonPersonalService.delete(id);

        try {
            return Response.ok().build();
        }
        catch (RuntimeException e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @DELETE
    @Path("/deleteall")
    public Response deleteAll()  {
        pokemonPersonalService.deleteAll();

        try {
            return Response.ok().build();
        }
        catch (RuntimeException e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @PUT
    @Path("/update/{id}")
    public Response updateById(PokemonDetail pokemonDetail, @PathParam("id") final String id) {
        pokemonPersonalService.update(pokemonDetail, id);

        try {
            return Response.ok().build();
        }
        catch (RuntimeException e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }
}


package com.pokeapi.resource;

import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.domain.entities.PokemonForList;
import com.pokeapi.infrastructure.services.PokemonPersonalService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.status;

@Path("/pokepersonal")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PokePersonalResource {

    @Inject
    PokemonPersonalService pokemonPersonalService;

    @GET
    @Path("/todos/{name}")
    public Response pokemonPersonalDetailId(PokemonDetail pokemonDetail, @PathParam("name") final String name) {
        try {
            return Response.ok(pokemonPersonalService.listDetailId(name)).build();
        }
        catch (RuntimeException e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @GET
    @Path("/todos")
    public List<PokemonForList> listToPersonalBd() {
        return pokemonPersonalService.listPersonalBd();
    }

    @POST
    @Path("/create")
    public List<PokemonDetail> add(PokemonDetail pokemonDetail) {
        pokemonPersonalService.add(pokemonDetail);
        return pokemonPersonalService.listDetail();
    }

    @DELETE
    @Path("/delete/{name}")
    public List<PokemonDetail> deleteByName(@PathParam("name") final String name)  {
        pokemonPersonalService.delete(name);
        return pokemonPersonalService.listDetail();
    }

    @PUT
    @Path("/update/{name}")
    public List<PokemonDetail> updateById(@PathParam("name") final String name) {
        pokemonPersonalService.update(name);
        return pokemonPersonalService.listDetail();
    }
}


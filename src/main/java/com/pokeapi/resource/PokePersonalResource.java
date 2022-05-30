package com.pokeapi.resource;

import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.infrastructure.services.PokemonPersonalService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/pokepersonal")
public class PokePersonalResource {

    @Inject
    PokemonPersonalService pokemonPersonalService;

    @GET
    public List<PokemonDetail> list() {
        return pokemonPersonalService.list();
    }

    @POST
    @Path("/create")
    public List<PokemonDetail> add(PokemonDetail pokemonDetail) {
        pokemonPersonalService.add(pokemonDetail);
        return list();
    }

    @DELETE
    @Path("/{id}")
    public List<PokemonDetail> deleteById(String id) {
        pokemonPersonalService.delete(id);
        return pokemonPersonalService.list();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update/{id}")
    public List<PokemonDetail> updateById( String id) {
        pokemonPersonalService.update(id);
        return pokemonPersonalService.list();
    }
}


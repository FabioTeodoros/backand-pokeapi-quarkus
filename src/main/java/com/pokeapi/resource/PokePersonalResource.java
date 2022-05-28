package com.pokeapi.resource;

import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.infrastructure.services.PokemonService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/pokepersonal")
public class PokePersonalResource {

    @Inject
    PokemonService pokemonService;

    @GET
    public List<PokemonDetail> list() {
        return pokemonService.list();
    }

    @POST
    public List<PokemonDetail> add(PokemonDetail pokemonDetail) {
        pokemonService.add(pokemonDetail);
        return list();
    }

    @DELETE
    @Path("/{id}")
    public List<PokemonDetail> deleteById(String id) {
        pokemonService.delete(id);
        return pokemonService.list();
    }
}


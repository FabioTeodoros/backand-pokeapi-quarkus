package com.pokeapi.resource;

import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.domain.entities.PokemonForList;
import com.pokeapi.infrastructure.services.PokemonOficialDetailService;
import com.pokeapi.infrastructure.services.PokemonPersonalService;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.resteasy.reactive.ResponseStatus;

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
    @Path("/todos/{id}")
    public List<PokemonDetail> PokemonPersonalDetailId(@PathParam("id") final String id) {
            return pokemonPersonalService.listDetail(id);
    }

    @GET
    @Path("/todos")
    public List<PokemonForList> listToPersonalBd() {
        return pokemonPersonalService.listPersonalBd();
    }

    @POST
    @Path("/create/{id}")
    public List<PokemonDetail> add(PokemonDetail pokemonDetail, String id) {
        pokemonPersonalService.add(pokemonDetail);
        return pokemonPersonalService.listDetail(id);
    }

    @DELETE
    @Path("/delete/{id}")
    public List<PokemonDetail> deleteById(String id) {
        pokemonPersonalService.delete(id);
        return pokemonPersonalService.listDetail(id);
    }

    @PUT
    @Path("/update/{id}")
    public List<PokemonDetail> updateById( String id) {
        pokemonPersonalService.update(id);
        return pokemonPersonalService.listDetail(id);
    }
}


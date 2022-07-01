package com.pokeapi.resource;

import com.mongodb.MongoException;
import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.infrastructure.gateway.PokemonFullService;
import com.pokeapi.infrastructure.gateway.PokemonOfficialDetailService;
import com.pokeapi.infrastructure.mongodb.repositories.PokemonPersonalRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pokemon")
public class PokemonResource {

    @Inject
    public PokemonResource(
            final PokemonPersonalRepository pokemonPersonalRepository,
            final PokemonOfficialDetailService pokemonOfficialDetailService,
            final PokemonFullService pokemonFullService) {
        this.pokemonPersonalRepository = pokemonPersonalRepository;
        this.pokemonOfficialDetailService = pokemonOfficialDetailService;
        this.pokemonFullService = pokemonFullService;
    }

    @Inject
    PokemonPersonalRepository pokemonPersonalRepository;
    @Inject
    PokemonOfficialDetailService pokemonOfficialDetailService;
    @Inject
    PokemonFullService pokemonFullService;

    @Path("/official/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response detailOfficialId(@PathParam("id") final String id) {
        try {
            return Response.ok().entity(pokemonOfficialDetailService.buscaPokemonDetail(id)).build();
        } catch (Exception exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("model") String model) {
        try {
            return Response.ok().entity(pokemonFullService.pokemonGetValidation(model)).build();
        } catch (IllegalArgumentException illegalArgumentException) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (MongoException mongoException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/personal/{id}")
    @GET
    public Response detailPersonalId(@PathParam("id") String id) {
        try {
            return Response.ok(pokemonPersonalRepository.id(id)).build();
        } catch (Exception exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/personal")
    @POST
    public Response Insert(PokemonDetail pokemonDetail) {
        try {
            pokemonPersonalRepository.insert(pokemonDetail);
            return Response.status(Response.Status.CREATED).build();
        } catch (MongoException mongoException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/personal/{id}")
    @DELETE
    public Response deleteId(@PathParam("id") final String id) {
        try {
            pokemonPersonalRepository.delete(id);
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (MongoException mongoException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/personal/{id}")
    @PUT
    public Response updateId(String id, PokemonDetail pokemonDetail) {
        try {
            pokemonPersonalRepository.update(id, pokemonDetail);
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (MongoException mongoException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}


package com.pokeapi.resource;

import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.infrastructure.services.PokemonOficialListService;
import com.pokeapi.infrastructure.services.PokemonOficialDetailService;
import com.pokeapi.infrastructure.services.PokemonPersonalService;
import com.pokeapi.resource.validations.GetCheckinCommandByType;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pokemon")
public class PokemonResource {

    @Inject
    public PokemonResource(
            final PokemonPersonalService pokemonPersonalService,
            final PokemonOficialListService pokemonOficialListService,
            final PokemonOficialDetailService pokemonOficialDetailService,
            final GetCheckinCommandByType getCheckinCommandByType
    ){
        this.pokemonOficialListService = pokemonOficialListService;
        this.pokemonPersonalService = pokemonPersonalService;
        this.pokemonOficialDetailService = pokemonOficialDetailService;
        this.getCheckinCommandByType = getCheckinCommandByType;
    }

    @Inject
    PokemonOficialListService pokemonOficialListService;
    @Inject
    PokemonPersonalService pokemonPersonalService;
    @Inject
    PokemonOficialDetailService pokemonOficialDetailService;
    @Inject
    GetCheckinCommandByType getCheckinCommandByType;

    @Path("/official/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response pokemonDetailOfficialId(@PathParam("id") final String id){
        try{
            return Response.ok().entity(pokemonOficialDetailService.buscaPokemonDetail(id)).build();
        } catch (Exception e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response pokemonList(@QueryParam("model") String model) {
        try{
            return Response.ok().entity(getCheckinCommandByType.checkList(model)).build();
        } catch (Exception e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @Path("/personal/{id}")
    @GET
    public Response pokemonPersonalDetailId(@PathParam("id")String id){
        try {
            return Response.ok(pokemonPersonalService.PokemonDetailIdPersonal(id)).build();
        } catch (Exception e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @Path("/personal")
    @POST
    public Response pokemonPersonalAdd(PokemonDetail pokemonDetail) {
        try {
            pokemonPersonalService.pokemonPersonalCreate(pokemonDetail);
            return Response.ok().build();
        } catch (Exception e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @Path("/personal/{id}")
    @DELETE
    public Response pokemonPersonalDeleteId(@PathParam("id") final String id)  {
        try {
            pokemonPersonalService.pokemonPersonalDelete(id);
            return Response.ok().build();
        } catch (Exception e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @Path("/personal/all")
    @DELETE
    public Response pokemonPersonalDeleteAll()  {
        pokemonPersonalService.pokemonPersonalDeleteAll();
        try {
            return Response.ok().build();
        } catch (Exception e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @Path("/personal/{id}")
    @PUT
    public Response pokemonPersonalUpdateId(PokemonDetail pokemonDetail, String id) {
        try {
            pokemonPersonalService.pokemonPersonalUptade(pokemonDetail, id);
            return Response.ok().build();
        } catch (Exception e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }
}


package com.pokeapi.resource;

import com.mongodb.MongoException;
import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.infrastructure.services.PokemonOficialAllPokemonService;
import com.pokeapi.infrastructure.services.PokemonOficialDetailService;
import com.pokeapi.infrastructure.services.PokemonPersonalService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pokemon")
public class PokemonResource {

    @Inject
    public PokemonResource(
            final PokemonPersonalService pokemonPersonalService,
            final PokemonOficialAllPokemonService pokemonOficialAllPokemonService,
            final PokemonOficialDetailService pokemonOficialDetailService
    ){
        this.pokemonOficialAllPokemonService = pokemonOficialAllPokemonService;
        this.pokemonPersonalService = pokemonPersonalService;
        this.pokemonOficialDetailService = pokemonOficialDetailService;
    }

    @Inject
    PokemonOficialAllPokemonService pokemonOficialAllPokemonService;
    @Inject
    PokemonPersonalService pokemonPersonalService;
    @Inject
    PokemonOficialDetailService pokemonOficialDetailService;

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
    public Response pokemonOfficialList(@QueryParam("model")String model) {
        try{
            return Response.ok().entity(pokemonOficialAllPokemonService.officialList(model)).build();
        } catch (Exception e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @Path("/personal/{id}")
    @GET
    public Response pokemonPersonalDetailId(@PathParam("id") final String id){
        try {
            return Response.ok(pokemonPersonalService.PokemonDetailIdPersonal(id)).build();
        } catch (Exception e){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @Path("/list")
    @GET
    public Response pokemonPersonalList(@QueryParam("model")String model) {
        try{
            return Response.ok(pokemonPersonalService.pokemonListPersonal(model)).build();
        } catch (MongoException m){
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @Path("/list")
//    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Object pokemonAllList(String model) {
//
//        try {
//            List<PokemonForList> listaPersonal = pokemonPersonalService.pokemonListPersonal(model);
//            PokemonList listaOficial = pokemonOficialAllPokemonService.officialList(model);
//            Object[] resultList = new Object[]{listaPersonal, listaOficial};
//            return Response.ok(resultList).build();
//        } catch (Exception e){
//            return Response.status(Response.Status.NO_CONTENT).build();
//        }
//    }

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


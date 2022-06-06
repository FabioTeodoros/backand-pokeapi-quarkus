package com.pokeapi.resource;

import com.pokeapi.domain.entities.PokemonForList;
import com.pokeapi.domain.entities.PokemonList;
import com.pokeapi.infrastructure.services.PokemonOficialAllPokemonService;
import com.pokeapi.infrastructure.services.PokemonPersonalService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("pokealllist")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PokemonAllResource {

    @Inject
    public PokemonAllResource(
            final  PokemonOficialAllPokemonService pokemonOficialAllPokemonService,
            final PokemonPersonalService pokemonPersonalService
    ) {
        this.pokemonOficialAllPokemonService = pokemonOficialAllPokemonService;
        this.pokemonPersonalService = pokemonPersonalService;
    }

    @Inject
    PokemonOficialAllPokemonService pokemonOficialAllPokemonService;
    @Inject
    PokemonPersonalService pokemonPersonalService;

    @GET
    public Object pokemonAllList() {
        try {
            List<PokemonForList> listaPersonal = pokemonPersonalService.listPersonalBd();
            PokemonList listaOficial = pokemonOficialAllPokemonService.getAll();
            Object[] resultList = new Object[]{listaPersonal, listaOficial};
            return Response.ok(resultList).build();
        }
        catch (Exception e){
            return Response.status(Response.Status.NO_CONTENT).build();

        }
    }
}

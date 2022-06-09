package com.pokeapi.infrastructure.services;
import com.pokeapi.domain.entities.*;
import com.pokeapi.enums.PokemonModel;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/teste")
public class PokemonFullListResponse {

    final static Integer OFFSET = 1;
    final static String URL_IMAGE = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/{id}.svg";

    public PokemonFullListResponse(PokemonOficialListService pokemonOficialListService) {
        this.pokemonOficialListService = pokemonOficialListService;
    }

    @Inject
    PokemonOficialListService pokemonOficialListService;

    @Path("/teste")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPokemonOfficialList() {

        PokemonResponseList response = pokemonOficialListService.officialList();
        Integer count = Integer.valueOf(response.getCount());
        List <PokemonFull> result = new ArrayList<>();
        PokemonFullList pokemonFullList = new PokemonFullList();
        PokemonResponse current;
        PokemonFull newFull;

       for(Integer index=0; index<count; index++) {
           newFull = new PokemonFull();
           current = response.getResults().get(index);
           newFull.setId(index+OFFSET);
           newFull.setName(current.getName());
           newFull.setUrlImage(URL_IMAGE.replace("{id}", Integer.toString(index)));
           newFull.setModel(PokemonModel.OFFICIAL.getDescricao());

           result.add(newFull);
       }

       pokemonFullList.setResults(result);
       pokemonFullList.setCount(result.size());

       return Response.ok().entity(pokemonFullList).build();
    }
}

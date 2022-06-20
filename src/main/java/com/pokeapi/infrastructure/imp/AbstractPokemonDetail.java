package com.pokeapi.infrastructure.imp;

import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.infrastructure.gateway.PokemonOfficialDetailService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.logging.Logger;

public abstract class AbstractPokemonDetail implements PokemonOfficialDetailService {

    private final WebTarget target;
    private final String dominio;
    private static final Logger LOGGER = Logger.getLogger(AbstractPokemonDetail.class.getName());

    public AbstractPokemonDetail(String dominio) {
        this.dominio = (dominio);
        Client client = ClientBuilder.newClient();
        this.target = client.target(dominio);
    }

    protected abstract String buildPath(String id);

    @Override
    public PokemonDetail buscaPokemonDetail(String id){
        try{
            LOGGER.info(String.format("Buscando Pokemon id %s no site %s", id, dominio));
            return target.path(buildPath(id)).request().get(PokemonDetail.class);
        }
        catch (Exception exception){
            LOGGER.info("Error in Abstract Detail");
            throw exception;
        }
    }
}
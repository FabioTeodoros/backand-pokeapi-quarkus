package com.pokeapi.infrastructure.services;

import com.pokeapi.domain.entities.PokemonResponseList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.logging.Logger;

public abstract class AbstractOficialListService implements PokemonOficialListService {

    private static final Logger LOG = Logger.getLogger(AbstractOficialListService.class.getName());
    private final WebTarget target;
    private final String dominio;

    public AbstractOficialListService(String dominio) {
        this.dominio = insertTraillingSlash(dominio);
        Client client = ClientBuilder.newClient();
        this.target = client.target(dominio);
    }

    protected String insertTraillingSlash(String path) {
        return path.endsWith("/") ? path : path + "/";
    }

    @Override
    public PokemonResponseList officialList() {
            LOG.info(String.format("Buscando lista de Pokemons no site %s", dominio));
            return target.request().get(PokemonResponseList.class);
    }
}
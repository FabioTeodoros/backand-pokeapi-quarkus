package com.pokeapi.infrastructure.services;

import com.pokeapi.domain.entities.PokemonDetail;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.logging.Logger;

public abstract class AbstractPokemonOficialDetailService implements PokemonOficialDetailService {

    private static final Logger LOG = Logger.getLogger(AbstractPokemonOficialDetailService.class.getName());
    private final WebTarget target;
    private final String dominio;

    public AbstractPokemonOficialDetailService(String dominio) {
        this.dominio = insertTraillingSlash(dominio);
        Client client = ClientBuilder.newClient();
        this.target = client.target(dominio);
    }

    protected String insertTraillingSlash(String path){
        return path.endsWith("/") ? path: path + "/";
    }

    protected abstract String buildPath(String id);

    @Override
    public PokemonDetail buscaPokemonDetail(String id){
        LOG.info(String.format("Buscando Pokemon id %s no site %s",id, dominio));
        return target.path(buildPath(id)).request().get(PokemonDetail.class);
    }

}
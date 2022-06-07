package com.pokeapi.infrastructure.services;

import com.pokeapi.domain.entities.PokemonList;
import com.pokeapi.resource.exceptions.InvalidRequestException;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.logging.Logger;

public abstract class AbstractPokemonOficialAllPokemonServiceService implements PokemonOficialAllPokemonService {

    private static final Logger LOG = Logger.getLogger(AbstractPokemonOficialAllPokemonServiceService.class.getName());
    private final WebTarget target;
    private final String dominio;

    final static String OFFICIAL = "official";

    public AbstractPokemonOficialAllPokemonServiceService(String dominio) {
        this.dominio = insertTraillingSlash(dominio);
        Client client = ClientBuilder.newClient();
        this.target = client.target(dominio);
    }

    protected String insertTraillingSlash(String path) {
        return path.endsWith("/") ? path : path + "/";
    }

    @Override
    public PokemonList officialList(@NotNull String model) {
        if (model.equals(OFFICIAL)) {
            LOG.info(String.format("Buscando lista de Pokemons no site %s", dominio));
            return target.request().get(PokemonList.class);
        } else {
            throw new InvalidRequestException("Error parameter invalid");
        }
    }
}
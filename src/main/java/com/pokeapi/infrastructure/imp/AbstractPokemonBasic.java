package com.pokeapi.infrastructure.imp;

import com.pokeapi.domain.entities.PokemonBasicResponsesPokeApi;
import com.pokeapi.infrastructure.gateway.PokemonOficialListService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.logging.Logger;

public abstract class AbstractPokemonBasic implements PokemonOficialListService {

    private static final Logger LOGGER = Logger.getLogger(AbstractPokemonBasic.class.getName());
    private final WebTarget target;
    private final String domain;

    public AbstractPokemonBasic(String domain) {
        this.domain = (domain);
        Client client = ClientBuilder.newClient();
        this.target = client.target(domain);
    }

    @Override
    public PokemonBasicResponsesPokeApi officialList() {
        try {
            LOGGER.info(String.format("Buscando lista de Pokemons no site %s", domain));
            return target.request().get(PokemonBasicResponsesPokeApi.class);
        } catch (Exception exception) {
            LOGGER.info("Error");
            throw exception;
        }
    }
}
package com.pokeapi.infrastructure.imp;

import com.pokeapi.domain.entities.PokemonBasicResponsesPokeApi;
import com.pokeapi.infrastructure.gateway.PokemonOficialBasicService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.logging.Logger;

public abstract class AbstractPokemonBasic implements PokemonOficialBasicService {

    private final WebTarget webTarget;
    private final String domain;
    private static final Logger LOGGER = Logger.getLogger(AbstractPokemonBasic.class.getName());

    public AbstractPokemonBasic(String domain) {
        this.domain = (domain);
        Client client = ClientBuilder.newClient();
        this.webTarget = client.target(domain);
    }

    @Override
    public PokemonBasicResponsesPokeApi officialList() {
        try {
            LOGGER.info(String.format("Buscando lista de Pokemons no site %s", domain));
            return webTarget.request().get(PokemonBasicResponsesPokeApi.class);
        } catch (Exception exception) {
            LOGGER.info("Error");
            throw exception;
        }
    }
}
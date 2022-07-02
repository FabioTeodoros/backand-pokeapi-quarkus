package com.pokeapi.service;

import com.pokeapi.domain.entities.*;
import com.pokeapi.domain.enums.PokemonModel;
import com.pokeapi.infrastructure.gateway.PokemonFullService;
import com.pokeapi.infrastructure.gateway.PokemonOficialBasicService;
import com.pokeapi.infrastructure.gateway.PokemonPersonalRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class PokemonFullContractService implements PokemonFullService {

    final static Integer OFFSET = 1;
    final static Integer OFFSET_BIG = 10000;
    final static Integer OFFSET_SMALL = 897;
    final static String URL_IMAGE_OFFICIAL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/{id}.png";
    final static String URL_IMAGE_PERSONAL = "https://rickandmortyapi.com/api/character/avatar/{id}.jpeg";
    private static final Logger LOGGER = Logger.getLogger(PokemonFullContractService.class.getName());

    public PokemonFullContractService(
            PokemonOficialBasicService pokemonOficialBasicService,
            PokemonPersonalRepository pokemonPersonalRepository
    ) {
        this.pokemonOficialBasicService = pokemonOficialBasicService;
        this.pokemonPersonalRepository = pokemonPersonalRepository;
    }

    @Inject
    PokemonOficialBasicService pokemonOficialBasicService;
    @Inject
    PokemonPersonalRepository pokemonPersonalRepository;

    public PokemonFullContractFull getPokemonOfficial() {
        PokemonFullContractFull pokemonFullContracts = new PokemonFullContractFull();
        try {
            PokemonBasicResponsesPoke pokemonBasicResponsesPoke = pokemonOficialBasicService.officialList();
            List<PokemonFullContractBasic> resultOfficial = new ArrayList<>();
            PokemonFullContractBasic pokemonFullContractBasic;

            for (int index = 0; index < pokemonBasicResponsesPoke.getCount(); index++) {
                pokemonFullContractBasic = new PokemonFullContractBasic();
                pokemonFullContractBasic.setName(pokemonBasicResponsesPoke.getResults().get(index).getName());
                pokemonFullContractBasic.setModel(PokemonModel.OFFICIAL.getDescriptor());
                if (index <= OFFSET_SMALL){                                                    //ponto a repensar a quantidade de pokes
                    pokemonFullContractBasic.setId(String.valueOf((index + OFFSET)));
                    pokemonFullContractBasic.setUrlImage(URL_IMAGE_OFFICIAL.replace("{id}",
                            Integer.toString(index + OFFSET)));
                } else {
                    pokemonFullContractBasic.setId(String.valueOf(((OFFSET_BIG - OFFSET_SMALL)+index)));
                    pokemonFullContractBasic.setUrlImage(URL_IMAGE_OFFICIAL.replace("{id}",
                            Integer.toString((OFFSET_BIG - OFFSET_SMALL)+index)));
                }
                resultOfficial.add(pokemonFullContractBasic);
            }
            pokemonFullContracts.setResults(resultOfficial);
            LOGGER.info("Get pokemon Official is Success");
        } catch (Exception exception){
            LOGGER.info("Error Service getPokemonOfficial");
        }return pokemonFullContracts;
    }

    public PokemonFullContractFull getPokemonPersonal() {
        PokemonFullContractFull pokemonFullContractFull = new PokemonFullContractFull();
        try {
            List<PokemonDetail> pokemonsDetail = pokemonPersonalRepository.list();
            List<PokemonFullContractBasic> pokemonResult = new ArrayList<>();
            pokemonsDetail.forEach(pokemonDetail -> {
                PokemonFullContractBasic pokemonFullContractBasic = new PokemonFullContractBasic();
                pokemonFullContractBasic.setName(pokemonDetail.getName());
                pokemonFullContractBasic.setId(pokemonDetail.getId());
                pokemonFullContractBasic.setModel((PokemonModel.PERSONAL.getDescriptor()));
                pokemonFullContractBasic.setUrlImage(URL_IMAGE_PERSONAL.replace("{id}",
                        (Integer.toString((pokemonResult.size()) + OFFSET))));
                pokemonResult.add(pokemonFullContractBasic);
            });
            pokemonFullContractFull.setResults(pokemonResult);
            LOGGER.info("Get pokemon Personal is Success");
        } catch (Exception exception) {
            LOGGER.info("Error Service getPokemonPersonal");
        }return pokemonFullContractFull;
    }

    public PokemonFullContractFull getPokemonAll() {
        PokemonFullContractFull pokemonFullContracts = new PokemonFullContractFull();
        try {
            List<PokemonFullContractBasic> pokemonFullContractBasicsOfficial = this.getPokemonOfficial().getResults();
            List<PokemonFullContractBasic> pokemonFullContractBasicsPersonal = this.getPokemonPersonal().getResults();
            List<PokemonFullContractBasic> pokemonFullContractBasicsAll = Stream.
                    concat(pokemonFullContractBasicsOfficial.stream(),
                    pokemonFullContractBasicsPersonal.stream()).collect(Collectors.toList());
            pokemonFullContracts.setResults(pokemonFullContractBasicsAll);
            LOGGER.info("Service getPokemonAll Success");
        } catch (Exception exception){
            LOGGER.info("Error Service getPokemonAll");
        }return pokemonFullContracts;
    }

    public PokemonFullContractFull pokemonGetValidation(String model) {
        try {
            if (model.equals(PokemonModel.PERSONAL.getDescriptor())) {
                return getPokemonPersonal();
            } else if (model.equals(PokemonModel.OFFICIAL.getDescriptor())) {
                return getPokemonOfficial();
            } else if (model.equals(PokemonModel.ALL.getDescriptor())) {
                return getPokemonAll();
            }return null;
        } catch (Exception exception) {
            LOGGER.info("Error Service pokemonGetValidation");
            return null;
        }
    }
}
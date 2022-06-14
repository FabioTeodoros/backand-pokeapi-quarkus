package com.pokeapi.service;

import com.pokeapi.domain.entities.PokemonFullContract;
import com.pokeapi.domain.entities.PokemonFullContracts;
import com.pokeapi.domain.entities.PokemonBasicResponsePokeApi;
import com.pokeapi.domain.entities.PokemonBasicResponsesPokeApi;
import com.pokeapi.domain.enums.PokemonModel;
import com.pokeapi.infrastructure.gateway.PokemonOficialListService;
import com.pokeapi.infrastructure.mongodb.repositories.PokemonPersonalRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PokemonFullContractService {

    final static Integer OFFSET = 1;
    final static String URL_IMAGE_OFFICIAL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/{id}.svg";
    final static String URL_IMAGE_PERSONAL = "https://rickandmortyapi.com/api/character/avatar/{id}.jpeg";
    Logger LOGGER = Logger.getLogger(PokemonFullContractService.class.getName());

    public PokemonFullContractService(
            PokemonOficialListService pokemonOficialListService,
            PokemonPersonalRepository pokemonPersonalRepository
    ) {
        this.pokemonOficialListService = pokemonOficialListService;
        this.pokemonPersonalRepository = pokemonPersonalRepository;
    }

    private static List<PokemonFullContract> resultAll = null;

    @Inject
    PokemonOficialListService pokemonOficialListService;
    @Inject
    PokemonPersonalRepository pokemonPersonalRepository;

    private PokemonFullContracts getPokemonOfficial() {
        try {
            PokemonBasicResponsesPokeApi responseOfficial = pokemonOficialListService.officialList();
            Integer countOfficial = responseOfficial.getCount();
            List<PokemonFullContract> resultOff = new ArrayList<>();
            PokemonFullContracts pokemonFullContracts = new PokemonFullContracts();
            resultAll = new ArrayList<>();
            PokemonBasicResponsePokeApi current;
            PokemonFullContract newFull;

            for (int index = 0; index < countOfficial; index++) {
                newFull = new PokemonFullContract();
                current = responseOfficial.getResults().get(index);
                newFull.setId(String.valueOf((index + OFFSET)));
                newFull.setName(current.getName());
                newFull.setUrlImage(URL_IMAGE_OFFICIAL.replace("{id}", Integer.toString(index + OFFSET)));
                newFull.setModel(PokemonModel.OFFICIAL.getDescriptor());
                resultOff.add(newFull);
                resultAll.add(newFull);
            }
            pokemonFullContracts.setResults(resultOff);
            pokemonFullContracts.setCount(resultOff.size());

            return pokemonFullContracts;
        } catch (Exception exception){
            LOGGER.info("Error Service getPokemonOfficial");
            throw exception;
        }
    }

    private PokemonFullContracts getPokemonPersonal(String model) {
        try {
            PokemonBasicResponsesPokeApi responsePersonal = pokemonPersonalRepository.pokemonListPersonal(model);
            Integer countPersonal = responsePersonal.getCount();
            List<PokemonFullContract> resultPer = new ArrayList<>();
            PokemonFullContracts pokemonFullContracts = new PokemonFullContracts();
            resultAll = new ArrayList<>();
            PokemonBasicResponsePokeApi current;
            PokemonFullContract newFull;

            for (int index = 0; index < countPersonal; index++) {
                newFull = new PokemonFullContract();
                current = responsePersonal.getResults().get(index);
                newFull.setId(current.getId());
                newFull.setName(current.getName());
                newFull.setModel(PokemonModel.PERSONAL.getDescriptor());
                newFull.setUrlImage(URL_IMAGE_PERSONAL.replace("{id}", (Integer.toString(index + OFFSET))));
                resultPer.add(newFull);
                resultAll.add(newFull);
            }

            pokemonFullContracts.setResults(resultPer);
            pokemonFullContracts.setCount(resultPer.size());

            return pokemonFullContracts;
        }
        catch (Exception exception){
            LOGGER.info("Error Service getPokemonPersonal");
            throw exception;
        }
    }

    private PokemonFullContracts getPokemonAll(String model) {
        try {
            PokemonFullContracts pokemonFullContracts = new PokemonFullContracts();
            Integer countOfficial = getPokemonOfficial().getCount();
            Integer countPersonal = getPokemonPersonal(model).getCount();
            List<PokemonFullContract> resultsPersonal = getPokemonPersonal(model).getResults();
            List<PokemonFullContract> resultsOfficial = getPokemonOfficial().getResults();
            List<PokemonFullContract> resultsFull = new ArrayList<>();
            resultsFull.addAll(resultsPersonal);
            resultsFull.addAll(resultsOfficial);

            pokemonFullContracts.setCount(countOfficial + countPersonal);
            pokemonFullContracts.setResults(resultsFull);

            return pokemonFullContracts;
        }
        catch (Exception exception){
            LOGGER.info("Error Service setPokemonAll");
            throw exception;
        }
    }
    public PokemonFullContracts pokemonGetValidation(String model) {
        try {
            if (model.equals(PokemonModel.PERSONAL.getDescriptor())) {
                return getPokemonPersonal(model);
            } else if (model.equals(PokemonModel.OFFICIAL.getDescriptor())) {
                return getPokemonOfficial();
            } else if (model.equals(PokemonModel.ALL.getDescriptor())) {
                return getPokemonAll(model);
            }
            return null;
        }
        catch (Exception exception){
            LOGGER.info("Error Service pokemonGetValidation");
            throw exception;
        }
    }
}
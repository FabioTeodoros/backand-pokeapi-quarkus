package com.pokeapi.service;

import com.pokeapi.domain.entities.PokemonFullContract;
import com.pokeapi.domain.entities.PokemonFullContracts;
import com.pokeapi.domain.entities.PokemonBasicResponse;
import com.pokeapi.domain.entities.PokemonBasicResponsesPokeApi;
import com.pokeapi.domain.enums.PokemonModel;
import com.pokeapi.infrastructure.gateway.PokemonFullService;
import com.pokeapi.infrastructure.gateway.PokemonOficialBasicService;
import com.pokeapi.infrastructure.mongodb.repositories.PokemonPersonalRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class  PokemonFullContractService implements PokemonFullService {

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

    public PokemonFullContracts getPokemonOfficial() {
        try {
             PokemonBasicResponsesPokeApi responseOfficial = pokemonOficialBasicService.officialList();
             Integer countOfficial = responseOfficial.getCount();
             List<PokemonFullContract> resultOfficial = new ArrayList<>();
             PokemonFullContracts pokemonFullContracts = new PokemonFullContracts();
             PokemonBasicResponse current;
             PokemonFullContract newFull;

            for (int index = 0; index < countOfficial; index++) {
                newFull = new PokemonFullContract();
                current = responseOfficial.getResults().get(index);
                newFull.setName(current.getName()); //verificar como fazer isso.
                newFull.setModel(PokemonModel.OFFICIAL.getDescriptor());
                if (index <= OFFSET_SMALL){
                    newFull.setId(String.valueOf((index + OFFSET)));
                    newFull.setUrlImage(URL_IMAGE_OFFICIAL.replace("{id}",
                            Integer.toString(index + OFFSET)));
                } else {
                    newFull.setId(String.valueOf(((OFFSET_BIG - OFFSET_SMALL)+index)));
                    newFull.setUrlImage(URL_IMAGE_OFFICIAL.replace("{id}",
                            Integer.toString((OFFSET_BIG - OFFSET_SMALL)+index)));
                }
                resultOfficial.add(newFull);
            }
            pokemonFullContracts.setResults(resultOfficial);
            pokemonFullContracts.setCount(resultOfficial.size());
            LOGGER.info("Get pokemon Official is Success");
            return pokemonFullContracts;
        } catch (Exception exception){
            LOGGER.info("Error Service getPokemonOfficial");
            return null;
        }
    }

    public PokemonFullContracts getPokemonPersonal(String model) {
        try {
            PokemonBasicResponsesPokeApi responsePersonal = pokemonPersonalRepository.list(model);
            Integer countPersonal = responsePersonal.getCount();
            List<PokemonFullContract> resultPer = new ArrayList<>();
            PokemonFullContracts pokemonFullContracts = new PokemonFullContracts();
            PokemonBasicResponse current;
            PokemonFullContract newFull;

            for (int index = 0; index < countPersonal; index++) {
                newFull = new PokemonFullContract();
                current = responsePersonal.getResults().get(index);
                newFull.setId(current.getId());
                newFull.setName(current.getName());
                newFull.setModel(PokemonModel.PERSONAL.getDescriptor());
                newFull.setUrlImage(URL_IMAGE_PERSONAL.replace("{id}", (Integer.toString(index + OFFSET))));
                resultPer.add(newFull);
            }
            pokemonFullContracts.setResults(resultPer);
            pokemonFullContracts.setCount(resultPer.size());
            LOGGER.info("Get pokemon Official is Success");
            return pokemonFullContracts;
        }
        catch (Exception exception){
            LOGGER.info("Error Service getPokemonPersonal");
            return null;
        }
    }

    public PokemonFullContracts getPokemonAll(String model) {
        try {
            PokemonFullContracts pokemonFullContracts = new PokemonFullContracts();
            PokemonFullContracts pokemonOfficial = this.getPokemonOfficial();
            PokemonFullContracts pokemonPersonal = this.getPokemonPersonal(model);
            Integer countOfficial = pokemonOfficial.getCount();
            Integer countPersonal = pokemonPersonal.getCount();
            List<PokemonFullContract> resultsOfficial = pokemonOfficial.getResults();
            List<PokemonFullContract> resultsPersonal = pokemonPersonal.getResults();
            List<PokemonFullContract> resultsFull = new ArrayList<>();
            resultsFull.addAll(resultsPersonal);
            resultsFull.addAll(resultsOfficial);

            pokemonFullContracts.setCount(countOfficial + countPersonal);
            pokemonFullContracts.setResults(resultsFull);
            LOGGER.info("Service getPokemonAll Success");
            return pokemonFullContracts;
        }
        catch (Exception exception){
            LOGGER.info("Error Service getPokemonAll" + exception);
            return null;
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
            return null;
        }
    }
}
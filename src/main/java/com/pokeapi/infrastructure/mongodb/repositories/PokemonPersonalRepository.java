package com.pokeapi.infrastructure.mongodb.repositories;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import com.pokeapi.domain.entities.*;

import org.bson.Document;
import org.bson.conversions.Bson;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

@ApplicationScoped
public class PokemonPersonalRepository {

    final static Long EXIST_BD = 0L;

    public MongoCollection collection() {
        return mongoClient.getDatabase("pokemonPersonal").getCollection("pokemonPersonal2", PokemonDetail.class);
    }

    public PokemonPersonalRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public Bson getId(String id) {
        return eq("_id", id);
    }

    private static final Logger LOGGER = Logger.getLogger(PokemonPersonalRepository.class.getName());

    @Inject
    MongoClient mongoClient;

    public PokemonDetail id(String id) {

        PokemonDetail pokemonDetail;
        try {
            FindIterable<PokemonDetail> pokemonDetailFind = collection().find(getId(id));
            pokemonDetail = pokemonDetailFind.first();
            if (pokemonDetail == null) {
                LOGGER.info("Pokemon Detail Personal Id not exist");
                return null;
            }
        } catch (MongoException mongoException) {
            LOGGER.info("Error in get Pokemon Personal Id with MongoBd");
            return null;
        } catch (Exception exception) {
            LOGGER.info("Exception in get Pokémons Detail Personal Id " + exception);
            return null;
        }
        LOGGER.info("Get Pokémon Detail Personal Id Success");
        return pokemonDetail;
    }

    public PokemonBasicResponsesPokeApi list(String model) {
        PokemonBasicResponsesPokeApi pokemonListPersonal = new PokemonBasicResponsesPokeApi();
        PokemonDetail pokemonDetail;
        try {
            FindIterable<PokemonDetail> pokemonDetailFind = collection().find();
            pokemonDetail = pokemonDetailFind.first();
            if (pokemonDetail == null) {
                LOGGER.info("These Pokémons doesn't exist");
                return null;
            }
                Bson projection = Projections.fields(Projections.include("name"));
                MongoCursor<PokemonDetail> cursor = collection().find().projection(projection).iterator();
                List<PokemonBasicResponse> listPersonal = new ArrayList<>();
                PokemonBasicResponse pokemonBasicResponse;
                while (cursor.hasNext()) {
                    pokemonDetail = cursor.next();
                    pokemonBasicResponse = new PokemonBasicResponse();
                    pokemonBasicResponse.setName(pokemonDetail.getName());
                    pokemonBasicResponse.setId(pokemonDetail.getId());
                    listPersonal.add(pokemonBasicResponse);
                }
                LOGGER.info("get Pokémons list Personal");
                pokemonListPersonal.setCount(listPersonal.size());
                pokemonListPersonal.setResults(listPersonal);

        } catch (MongoException mongoException) {
            LOGGER.info("Error with MongoBd");
        } catch (Exception exception) {
            LOGGER.info("error in get Pokémons personal list");
        }
        return pokemonListPersonal;
    }

    public void insert(PokemonDetail pokemonDetail) {
        try {
                pokemonDetail.setId(UUID.randomUUID().toString());
                collection().insertOne(pokemonDetail);
                LOGGER.info("Pokemon Personal Create Success");
        } catch (MongoException mongoException) {
            LOGGER.info("Error with MongoBd");
        } catch (Exception exception) {
            LOGGER.info("Error in Insert Pokémons");
        }
    }

    public void delete(String id) {
        try {
            long queryId = collection().countDocuments(getId(id));
            if (queryId == (EXIST_BD)){
                LOGGER.info("Error this id does not exists");
                throw new IllegalArgumentException("Incorrect Id");
            }
            Document document = new Document().append("_id", id);
            collection().deleteOne(document);
            LOGGER.info(String.format("deleted pokemon id: %s", id));
        } catch (MongoException mongoException) {
            LOGGER.info("Error with MongoBd");
        } catch (Exception exception) {
            LOGGER.info("Error in delete one Pokémon");
        }
    }

    public void update(String id, PokemonDetail pokemonDetail) {
        try {
            FindIterable<PokemonDetail> pokemonDetailFind = collection().find(getId(id));
            if (pokemonDetail == null) {
                LOGGER.info("Error update, this id does not exists");
            }
            String uuid = (UUID.randomUUID().toString());
            Document queryBd = new Document();
            queryBd.put("_id", id);
            Document newDocBd = new Document();
            newDocBd.put("_id", uuid);
            Document updateNewDocBd = new Document();
            updateNewDocBd.put("$set", newDocBd);
            collection().updateOne(queryBd, updateNewDocBd);
            LOGGER.info(String.format("Pokémon has been updated"));
        } catch (Exception exception) {
            LOGGER.info("Error in update Pokémons");
            throw exception;
        }
    }
}
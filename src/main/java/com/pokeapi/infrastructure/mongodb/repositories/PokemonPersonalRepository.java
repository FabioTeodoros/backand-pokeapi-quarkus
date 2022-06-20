package com.pokeapi.infrastructure.mongodb.repositories;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.pokeapi.domain.entities.*;

import com.pokeapi.resource.exceptions.InvalidRequestException;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


@ApplicationScoped
public class PokemonPersonalRepository {

    final static Integer EXIST_BD = 1;

    public MongoCollection getCollection() {
        return mongoClient.getDatabase("pokemonPersonal").getCollection("pokemonPersonal");
    }

    public PokemonPersonalRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public Bson getId(String id){
        return Filters.eq("id", id);
    }

    private Bson getList(){
        return Filters.empty();
    }

    private static final Logger LOGGER = Logger.getLogger(PokemonPersonalRepository.class.getName());

    @Inject
    MongoClient mongoClient;

    public List<PokemonDetail> pokemonDetailIdPersonal(String id) {
        try {
            List<PokemonDetail> pokemonDetails = new ArrayList<>();
            MongoCursor<Document> cursor = getCollection().find().iterator();
            long queryId = getCollection().countDocuments(getId(id));
            if (Long.toString(queryId).equals(EXIST_BD.toString())) {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    PokemonDetail pokemonDetail = new PokemonDetail();
                    pokemonDetail.setId(document.getString("id"));
                    pokemonDetail.setHeight(document.getString("height"));
                    pokemonDetail.setWeight(document.getString("weight"));
                    pokemonDetail.setName(document.getString("name"));
                    pokemonDetail.setBaseExperience(document.getString("base_experience"));
                    pokemonDetail.setTypes((List<PokemonTypes>) document.get("types"));
                    pokemonDetail.setAbilities((List<PokemonAbilities>) document.get("abilities"));
                    if (document.getString("id").equals(id)) {
                        pokemonDetails.add(pokemonDetail);
                        LOGGER.info("get detailed Pokémons Personal");
                    }
                }
                return pokemonDetails;
            } else {
                LOGGER.info("This Pokémon doesn't exist");
                throw new InvalidRequestException("This Pokémons doesn't exist");
            }
        }catch (MongoException mongoException) {
            LOGGER.info("Error with MongoBd");
            throw mongoException;
        }catch (Exception exception) {
            LOGGER.info("Error in get Pokémons");
            throw exception;
        }
    }

    public PokemonBasicResponsesPokeApi pokemonListPersonal(String model) {
        try {
            MongoCursor<Document> cursor = getCollection().find().iterator();
            long queryList = getCollection().countDocuments(getList());
            if (queryList >= EXIST_BD) {
                List<PokemonBasicResponsePokeApi> listPersonal = new ArrayList<>();
                PokemonBasicResponsesPokeApi pokemonListPersonal = new PokemonBasicResponsesPokeApi();
                PokemonBasicResponsePokeApi pokemonBasicResponsePokeApi;
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    pokemonBasicResponsePokeApi = new PokemonBasicResponsePokeApi();
                    pokemonBasicResponsePokeApi.setName(document.getString("name"));
                    pokemonBasicResponsePokeApi.setUrl(document.getString("url"));
                    pokemonBasicResponsePokeApi.setId(document.getString("id"));
                    listPersonal.add(pokemonBasicResponsePokeApi);
                }
                LOGGER.info("get Pokémons list Personal");
                pokemonListPersonal.setCount((int) queryList);
                pokemonListPersonal.setResults(listPersonal);
                return pokemonListPersonal;
            }else {
                LOGGER.info("These Pokémons doesn't exist");
                throw new InvalidRequestException("These Pokémons doesn't exist");
            }
        }catch (MongoException mongoException){
            LOGGER.info("Error with MongoBd");
            throw mongoException;
        } catch (Exception exception) {
            LOGGER.info("error in get Pokémons personal list");
            throw exception;
        }
    }

    public void pokemonPersonalCreate(PokemonDetail pokemonDetail) {
        try {
            String uuid = UUID.randomUUID().toString();
            long queryId = getCollection().countDocuments(getId(uuid));
            if (queryId < (EXIST_BD)) {
                Document document = new Document()
                        .append("id", uuid)
                        .append("height", pokemonDetail.getHeight())
                        .append("weight", pokemonDetail.getWeight())
                        .append("name", pokemonDetail.getName())
                        .append("base_experience", pokemonDetail.getBaseExperience())
                        .append("types", pokemonDetail.getTypes())
                        .append("abilities", pokemonDetail.getAbilities())
                        .append("sprites", pokemonDetail.getSprites());
                getCollection().insertOne(document);
                LOGGER.info("added pokemon");
            } else {
                LOGGER.info("Error this id already exists");
            }
        }catch (MongoException mongoException){
            LOGGER.info("Error with MongoBd");
            throw mongoException;
        } catch (Exception exception) {
            LOGGER.info("Error in add Pokémons");
            throw exception;
        }
    }

    public void pokemonPersonalDelete(String id) {
        try {
            long queryId = getCollection().countDocuments(getId(id));
            if (queryId >= (EXIST_BD)) {
                Document document = new Document().append("id", id);
                getCollection().deleteOne(document);
                LOGGER.info(String.format("deleted pokemon id: %s", id));
            } else {
                LOGGER.info("Error this id does not exists");
            }
        } catch (MongoException mongoException){
            LOGGER.info("Error with MongoBd");
            throw mongoException;
        } catch (Exception exception){
            LOGGER.info("Error in delete one Pokémon");
            throw exception;
        }
    }

    public void pokemonPersonalUpdate(PokemonDetail pokemonDetail, String id) {
        try {
            String uuid = UUID.randomUUID().toString();
            long queryId = getCollection().countDocuments(getId(id));
            if (Long.toString(queryId).equals(EXIST_BD.toString())) {
                Document document = new Document().append("id", id);
                getCollection().deleteOne(document);
                document.append("id", uuid)
                        .append("height", pokemonDetail.getHeight())
                        .append("weight", pokemonDetail.getWeight())
                        .append("name", pokemonDetail.getName())
                        .append("base_experience", pokemonDetail.getBaseExperience())
                        .append("types", pokemonDetail.getTypes())
                        .append("abilities", pokemonDetail.getAbilities())
                        .append("sprites", pokemonDetail.getSprites());
                getCollection().insertOne(document);
                LOGGER.info(String.format("Pokémon id: %s has been updated", uuid));
            } else {
                LOGGER.info("Error update, this id does not exists");
            }
        }catch (Exception exception) {
            LOGGER.info("Error in update Pokémons");
            throw exception;
        }
    }
}
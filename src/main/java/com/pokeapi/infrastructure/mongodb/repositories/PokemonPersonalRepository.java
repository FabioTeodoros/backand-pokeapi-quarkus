package com.pokeapi.infrastructure.mongodb.repositories;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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

    public List<PokemonDetail> pokemonDetailIdPersonal(String id) {
        LOGGER.info("entrou aqui");
        List<PokemonDetail> pokemonDetails = new ArrayList<>();
        try {
            FindIterable<PokemonDetail> pokemonDetailFind = collection().find(getId(id));
            PokemonDetail pokemonDetail = pokemonDetailFind.first();
            if (pokemonDetail == null) {
                LOGGER.info("Pokemon id not exist");
                return pokemonDetails;
            }
            pokemonDetails.add(pokemonDetail);

        } catch (MongoException mongoException) {
            LOGGER.info("Error in get PokemonPersonalId with MongoBd");
        } catch (Exception exception) {
            LOGGER.info("Exception in get Pokémons " + exception.toString());
        }
        return pokemonDetails;
    }

    public PokemonBasicResponsesPokeApi pokemonListPersonal(String model) {
        PokemonBasicResponsesPokeApi pokemonListPersonal = new PokemonBasicResponsesPokeApi();
        try {
            if (collection().find().first() != null) {
                MongoCursor<Document> cursor = collection().find().iterator();
                List<PokemonBasicResponsePokeApi> listPersonal = new ArrayList<>();
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
                pokemonListPersonal.setCount(listPersonal.size());
                pokemonListPersonal.setResults(listPersonal);
            } else {
                LOGGER.info("These Pokémons doesn't exist");
            }
        } catch (MongoException mongoException) {
            LOGGER.info("Error with MongoBd");
        } catch (Exception exception) {
            LOGGER.info("error in get Pokémons personal list");
            ;
        }
        return pokemonListPersonal;
    }

    public void pokemonPersonalCreate(PokemonDetail pokemonDetail) {
        try {
                pokemonDetail.setId(UUID.randomUUID().toString());
                collection().insertOne(pokemonDetail);
                LOGGER.info("added pokemon");

//            String uuid = UUID.randomUUID().toString();
//            long queryId = collection().countDocuments(getId(uuid));
//            if (queryId == (EXIST_BD)) {
//                Document document = new Document()
//                        .append("id", uuid)
//                        .append("height", pokemonDetail.getHeight())
//                        .append("weight", pokemonDetail.getWeight())
//                        .append("name", pokemonDetail.getName())
//                        .append("base_experience", pokemonDetail.getBaseExperience())
//                        .append("types", pokemonDetail.getTypes())
//                        .append("abilities", pokemonDetail.getAbilities())
//                        .append("sprites", pokemonDetail.getSprites());
//                collection().insertOne(document);
//                LOGGER.info("added pokemon");
//            } else {
//                LOGGER.info("Error this id already exists");
//            }
        } catch (MongoException mongoException) {
            LOGGER.info("Error with MongoBd");
        } catch (Exception exception) {
            LOGGER.info("Error in add Pokémons");
        }
    }

    public void pokemonPersonalDelete(String id) {
        try {
            long queryId = collection().countDocuments(getId(id));
            if (queryId >= (EXIST_BD)) {
                Document document = new Document().append("id", id);
                collection().deleteOne(document);
                LOGGER.info(String.format("deleted pokemon id: %s", id));
            } else {
                LOGGER.info("Error this id does not exists");
            }
        } catch (MongoException mongoException) {
            LOGGER.info("Error with MongoBd");
            throw mongoException;
        } catch (Exception exception) {
            LOGGER.info("Error in delete one Pokémon");
            throw exception;
        }
    }

    public void pokemonPersonalUpdate(PokemonDetail pokemonDetail, String id) {
        try {
            String uuid = UUID.randomUUID().toString();
            long queryId = collection().countDocuments(getId(id));
            if (queryId > EXIST_BD) {
                Document document = new Document().append("id", id);
                collection().deleteOne(document);
                document.append("id", uuid)
                        .append("height", pokemonDetail.getHeight())
                        .append("weight", pokemonDetail.getWeight())
                        .append("name", pokemonDetail.getName())
                        .append("base_experience", pokemonDetail.getBaseExperience())
                        .append("types", pokemonDetail.getTypes())
                        .append("abilities", pokemonDetail.getAbilities())
                        .append("sprites", pokemonDetail.getSprites());
                collection().insertOne(document);
                LOGGER.info(String.format("Pokémon id: %s has been updated", uuid));
            } else {
                LOGGER.info("Error update, this id does not exists");
            }
        } catch (Exception exception) {
            LOGGER.info("Error in update Pokémons");
            throw exception;
        }
    }
}
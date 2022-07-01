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
public class PokemonPersonalRepository implements com.pokeapi.infrastructure.gateway.PokemonPersonalRepository {

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

    @Override
    public PokemonDetail id(String id) {
        PokemonDetail pokemonDetail = new PokemonDetail();
        try {
            FindIterable<PokemonDetail> pokemonDetailFind = collection().find(getId(id));
            pokemonDetail = pokemonDetailFind.first();
            if (pokemonDetail == null) {
                LOGGER.info("Pokemon Detail Personal Id not exist");
                return pokemonDetail;
            }
        } catch (MongoException mongoException) {
            LOGGER.info("Error in get Pokemon Personal Id with MongoBd");
        } catch (Exception exception) {
            LOGGER.info("Exception in get Pokémon Detail Personal Id ");
        }LOGGER.info("Get Pokémon Detail Personal Id Success");
        return pokemonDetail;
    }

    @Override
    public List<PokemonDetail> list() {
        List<PokemonDetail> pokemonsPersonal = new ArrayList<>();
        try {
            Bson projection = Projections.fields(Projections.include("name"));
            MongoCursor<PokemonDetail> cursor = collection().find().projection(projection).iterator();
            while (cursor.hasNext()) {
                pokemonsPersonal.add(cursor.next());
            }
        } catch (MongoException mongoException) {
            LOGGER.info("Error with MongoBd");
        } catch (Exception exception) {
            LOGGER.info("error in get Pokémons personal list");
        } LOGGER.info("Get pokemon list personal success");
        return pokemonsPersonal;
    }

    @Override
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

    @Override
    public void delete(String id) {
        try {
            if(collection().deleteOne(getId(id)).getDeletedCount() == EXIST_BD) {
                LOGGER.info("Error this id does not exists");
            }else LOGGER.info(String.format("deleted pokemon id: %s", id));
        } catch (MongoException mongoException) {
            LOGGER.info("Error with MongoBd");
        } catch (Exception exception) {
            LOGGER.info("Error in delete one Pokémon");
        }
    }

    @Override
    public void update(String id, PokemonDetail pokemonDetail) {
        try {
                Document command = new Document("$set", pokemonDetail);
                if(collection().updateOne(getId(id), command).getMatchedCount() == EXIST_BD){
                    LOGGER.info("Error update, this id does not exists");
                }else LOGGER.info(String.format("Pokémon has been updated"));
        } catch (MongoException mongoException) {
            LOGGER.info("Error with MongoBd");
        }catch (Exception exception) {
            LOGGER.info("Error in update Pokémons");
        }
    }
}
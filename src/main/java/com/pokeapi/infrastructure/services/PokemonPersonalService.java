package com.pokeapi.infrastructure.services;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.pokeapi.domain.entities.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.eq;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static com.mongodb.client.model.Updates.combine;

@ApplicationScoped
public class PokemonPersonalService {

    private static final Logger LOG = Logger.getLogger(PokemonPersonalService.class.getName());
    @Inject
    MongoClient mongoClient;

    public List<PokemonDetail> listDetail() {
        List<PokemonDetail> pokemonDetails = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                PokemonDetail pokemonDetail = new PokemonDetail();
                pokemonDetail.setId(document.getString("id"));
                pokemonDetail.setHeight(document.getString("height"));
                pokemonDetail.setWeight(document.getString("weight"));
                pokemonDetail.setName(document.getString("name"));
                pokemonDetail.setBaseExperience(document.getString("base_experience"));
                pokemonDetail.setTypes((List<TypeOfType>) document.get("types"));
                pokemonDetail.setAbilities((List<AbilitiesOfAbility>) document.get("abilities"));
                //pokemonDetail.setSprites((Sprite) document.get("sprites"));

                pokemonDetails.add(pokemonDetail);

                }
        } finally {
            cursor.close();
        }
        return pokemonDetails;
    }

    public List<PokemonDetail> listDetailId(String name) {
        List<PokemonDetail> pokemonDetails = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                PokemonDetail pokemonDetail = new PokemonDetail();
                pokemonDetail.setId(document.getString("id"));
                pokemonDetail.setHeight(document.getString("height"));
                pokemonDetail.setWeight(document.getString("weight"));
                pokemonDetail.setName(document.getString("name"));
                pokemonDetail.setBaseExperience(document.getString("base_experience"));
                pokemonDetail.setTypes((List<TypeOfType>) document.get("types"));
                pokemonDetail.setAbilities((List<AbilitiesOfAbility>) document.get("abilities"));
                //pokemonDetail.setSprites((Sprite) document.get("sprites"));
                if(document.getString("name").equals(name)){
                    pokemonDetails.add(pokemonDetail);
                }
            }
        }
        finally {
            cursor.close();
        }
        LOG.info(String.format("get detailed Pokémons Personal"));
        return pokemonDetails;
    }

    public List<PokemonForList> listPersonalBd() {
        List<PokemonForList> listPersonalBd = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                PokemonForList pokemonForList = new PokemonForList();
                pokemonForList.setName(document.getString("name"));
                pokemonForList.setUrl(document.getString("url"));

                listPersonalBd.add(pokemonForList);
            }
        }
        finally {
            cursor.close();
        }
        LOG.info(String.format("get Pokémons list Personal"));
        return listPersonalBd;
    }

    public void add (PokemonDetail pokemonDetail) {
        String uuid = UUID.randomUUID().toString();
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

            LOG.info(String.format("added pokemon"));
    }


    public void delete(String name) {
            Document document = new Document()
                    .append("name", name);
            getCollection().deleteOne(document);

            LOG.info(String.format("deleted pokemon name: %s", name));
    }

    public List<PokemonDetail> update(String name) {
        List<PokemonDetail> update = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next().append("name", name);
                Document updateDoc = new Document().append("name", name);
                PokemonDetail pokemonDetail = new PokemonDetail();
                pokemonDetail.setId(document.getString("id"));
                pokemonDetail.setHeight(document.getString("height"));
                pokemonDetail.setWeight(document.getString("weight"));
                pokemonDetail.setName(document.getString("name"));
                pokemonDetail.setBaseExperience(document.getString("base_experience"));
                pokemonDetail.setTypes((List<TypeOfType>)document.get("types"));
                pokemonDetail.setAbilities((List<AbilitiesOfAbility>)document.get("abilities"));
                // pokemonDetail.setSprites((Sprite) document.get("sprites"));

                getCollection().updateOne(document, updateDoc);
            }
        } finally {
            cursor.close();
        }
        return update;
    }

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("pokemonDetail").getCollection("pokemonDetail");
    }
}
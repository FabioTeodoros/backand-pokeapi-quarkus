package com.pokeapi.infrastructure.services;

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

import static com.mongodb.client.model.Updates.combine;

@ApplicationScoped
public class PokemonPersonalService {

    @Inject
    MongoClient mongoClient;

    public List<PokemonDetail> listDetail(String id) {
        List<PokemonDetail> pokemonDetails = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next().append("id", id);
                PokemonDetail pokemonDetail = new PokemonDetail();
                pokemonDetail.setId(document.getString("id"));
                pokemonDetail.setHeight(document.getString("height"));
                pokemonDetail.setWeight(document.getString("weight"));
                pokemonDetail.setName(document.getString("name"));
                pokemonDetail.setBaseExperience(document.getString("base_experience"));
                pokemonDetail.setTypes((List<TypeOfType>)document.get("types"));
                pokemonDetail.setAbilities((List<AbilitiesOfAbility>)document.get("abilities"));
               // pokemonDetail.setSprites((Sprite) document.get("sprites"));

                pokemonDetails.add(pokemonDetail);
            }
        } finally {
            cursor.close();
        }
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
        } finally {
            cursor.close();
        }
        return listPersonalBd;
    }


    public void add(PokemonDetail pokemonDetail) {
        Document document = new Document()
                .append("id", pokemonDetail.getId())
                .append("height", pokemonDetail.getHeight())
                .append("weight", pokemonDetail.getWeight())
                .append("name", pokemonDetail.getName())
                .append("base_experience", pokemonDetail.getBaseExperience())
                .append("types", pokemonDetail.getTypes())
                .append("abilities", pokemonDetail.getAbilities())
                .append("sprites", pokemonDetail.getSprites());
        getCollection().insertOne(document);
    }

    public void delete(String id) {
        Document document = new Document()
                .append("id", id);
        getCollection().deleteOne(document);
    }

    public void update(String id) {
        Document document = new Document()
                .append("id", id);
        Document update = new Document()
                .append("id", id);
                getCollection().updateOne((Bson) document, (Bson) update);
    }

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("pokemonDetail").getCollection("pokemonDetail");
    }
}
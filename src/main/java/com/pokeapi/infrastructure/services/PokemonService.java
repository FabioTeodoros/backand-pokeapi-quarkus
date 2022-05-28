package com.pokeapi.infrastructure.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.domain.entities.TypeOfType;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class PokemonService {

    @Inject
    MongoClient mongoClient;

    public List<PokemonDetail> list() {
        List<PokemonDetail> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                PokemonDetail pokemonDetail = new PokemonDetail();
                pokemonDetail.setId(document.getString("id"));
                pokemonDetail.setHeight(document.getString("heigth"));
                pokemonDetail.setWeight(document.getString("weigth"));
                pokemonDetail.setName(document.getString("name"));
                pokemonDetail.setBaseExperience(document.getString("base_experience"));
                pokemonDetail.setTypes(document.getList("types", ));
                //pokemonDetail.setAbilities(document.getString("abilities"));
                //pokemonDetail.setSprites(document.getString("sprites"));

                list.add(pokemonDetail);
            }
        } finally {
            cursor.close();
        }
        return list;
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

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("pokemonDetail").getCollection("pokemonDetail");
    }
}
package com.pokeapi.infrastructure.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.pokeapi.domain.entities.PokemonDetail;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
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
                pokemonDetail.setName(document.getString("name"));
                list.add(pokemonDetail);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public void add(PokemonDetail pokemonDetail) {
        Document document = new Document()
                .append("name", pokemonDetail.getName());
        getCollection().insertOne(document);
    }

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("pokemonDetail").getCollection("pokemonDetail");
    }
}
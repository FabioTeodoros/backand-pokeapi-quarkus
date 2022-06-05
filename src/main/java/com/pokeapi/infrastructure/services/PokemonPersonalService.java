package com.pokeapi.infrastructure.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.pokeapi.domain.entities.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@ApplicationScoped
public class PokemonPersonalService {

    final Integer EXIST_BD = 1;

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("pokemonPersonal").getCollection("pokemonPersonal");
    }

    private static final Logger LOG = Logger.getLogger(PokemonPersonalService.class.getName());

    public PokemonPersonalService(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    private Bson getId(final String id){
        return Filters.eq("id", id);
    }

    private Bson getList(){
        return Filters.empty();
    }

    @Inject
    MongoClient mongoClient;

    public List<PokemonDetail> listDetailId(String id) {
        List<PokemonDetail> pokemonDetails = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();
        Long queryId = getCollection().countDocuments(getId(id));

        if (queryId.toString().equals(EXIST_BD.toString())) {
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

                    if(document.getString("id").equals(id)) {
                        pokemonDetails.add(pokemonDetail);
                        LOG.info(String.format("get detailed Pokémons Personal"));
                    }
                }
            }
            catch (RuntimeException e){
                LOG.info(String.format("Error in get Pokémons"));
            }
            finally {
                cursor.close();
            }
            return pokemonDetails;
        }
        else{
            LOG.info(String.format("This Pokémon doesn't exist"));
            return null;
        }
    }
    public List<PokemonForList> listPersonalBd() {
        List<PokemonForList> listPersonalBd = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();
        Long queryList = getCollection().countDocuments(getList());

        if (queryList >= EXIST_BD) {
            try {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    PokemonForList pokemonForList = new PokemonForList();
                    pokemonForList.setName(document.getString("name"));
                    pokemonForList.setUrl(document.getString("url"));

                    listPersonalBd.add(pokemonForList);
                }
                LOG.info(String.format("get Pokémons list Personal"));
                return listPersonalBd;
            }
            catch (RuntimeException e){
                LOG.info(String.format("Error in List Pokémons"));
                return null;
            }
            finally {
                cursor.close();
            }
        }
        else {
            LOG.info(String.format("This Pokémon list doesn't exist"));
            return null;
        }
    }

    public void add (PokemonDetail pokemonDetail, String id) {
        Long queryId = getCollection().countDocuments(getId(id));

        if (queryId < (EXIST_BD)) {
            try {
                //String uuid = UUID.randomUUID().toString(); caso fosse uuid
                Document document = new Document()
                        .append("id", id)
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
            catch (RuntimeException e){
                LOG.info(String.format("Error in add Pokémons"));
            }
        }
        else{
            LOG.info(String.format("Error this id already exists"));
        }

    }

    public void delete(String id) {
        Long queryId = getCollection().countDocuments(getId(id));

        if (queryId >= (EXIST_BD)) {
            try {
                Document document = new Document()
                        .append("id", id);
                getCollection().deleteOne(document);
                LOG.info(String.format("deleted pokemon id: %s", id));
            }
            catch (RuntimeException e){
                LOG.info(String.format("Error in delete one Pokémon"));
            }
        }
        else{
            LOG.info(String.format("Error this id does not exists"));
        }
    }

    public void deleteAll() {
        Long queryList = getCollection().countDocuments(getList());

        if (queryList >= (EXIST_BD)) {
            try {
                Document document = new Document();
                getCollection().drop();
                LOG.info(String.format("Deleted all Pokémons"));
            }
            catch (RuntimeException e){
                LOG.info(String.format("Error in delete all Pokémons"));
            }
        }
        else{
            LOG.info(String.format("there is no Pokémons"));
        }
    }

    public List<PokemonDetail> update(PokemonDetail pokemonDetail, String id) {
        List<PokemonDetail> update = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();
        Long queryId = getCollection().countDocuments(getId(id));

        if (queryId.toString().equals(EXIST_BD.toString())) {
            try {
                Document document = new Document()
                        .append("id", id);
                getCollection().deleteOne(document);

                document.append("id", pokemonDetail.getId())
                        .append("height", pokemonDetail.getHeight())
                        .append("weight", pokemonDetail.getWeight())
                        .append("name", pokemonDetail.getName())
                        .append("base_experience", pokemonDetail.getBaseExperience())
                        .append("types", pokemonDetail.getTypes())
                        .append("abilities", pokemonDetail.getAbilities())
                        .append("sprites", pokemonDetail.getSprites());
                getCollection().insertOne(document);

                LOG.info(String.format("Pokémon id: %s has been updated", id));
                return update;
            } catch (RuntimeException e) {
                LOG.info(String.format("Error in update Pokémons"));
                return null;
            }
            finally {
                cursor.close();
            }
        }
        else {
            LOG.info(String.format("Error update, this id does not exists"));
            return null;
        }
    }
}
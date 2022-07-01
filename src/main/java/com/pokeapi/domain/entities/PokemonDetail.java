package com.pokeapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

public class PokemonDetail {

    private String id;
    private String height;
    private String weight;
    private String name;
    private String baseExperience;
    private List <PokemonTypes> types;
    private List <PokemonAbilities> abilities;
    private PokemonImage sprites;

    public PokemonDetail() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PokemonDetail(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("base_experience")
    public String getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(String baseExperience) {
        this.baseExperience = baseExperience;
    }

    public List<PokemonTypes> getTypes() {
        return types;
    }

    public void setTypes(List<PokemonTypes> types) {
        this.types = types;
    }

    public List<PokemonAbilities> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<PokemonAbilities> abilities) {
        this.abilities = abilities;
    }

    public PokemonImage getSprites() {
        return sprites;
    }

    public void setSprites(PokemonImage sprites) {
        this.sprites = sprites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PokemonDetail)) return false;

        PokemonDetail that = (PokemonDetail) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (height != null ? !height.equals(that.height) : that.height != null) return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (baseExperience != null ? !baseExperience.equals(that.baseExperience) : that.baseExperience != null)
            return false;
        if (types != null ? !types.equals(that.types) : that.types != null) return false;
        if (abilities != null ? !abilities.equals(that.abilities) : that.abilities != null) return false;
        return sprites != null ? sprites.equals(that.sprites) : that.sprites == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (baseExperience != null ? baseExperience.hashCode() : 0);
        result = 31 * result + (types != null ? types.hashCode() : 0);
        result = 31 * result + (abilities != null ? abilities.hashCode() : 0);
        result = 31 * result + (sprites != null ? sprites.hashCode() : 0);
        return result;
    }
}

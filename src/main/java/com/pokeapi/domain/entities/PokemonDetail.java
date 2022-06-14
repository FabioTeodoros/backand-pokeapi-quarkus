package com.pokeapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

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

}

package com.pokeapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PokemonDetail {

    private String id;
    private String height;
    private String weight;
    private String name;
    private String baseExperience;
    private List <TypeOfType> types;
    private List <AbilitiesOfAbility> abilities;
    private Sprite sprites;

    public PokemonDetail() {
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

    public List<TypeOfType> getTypes() {
        return types;
    }

    public void setTypes(List<TypeOfType> types) {
        this.types = types;
    }

    public List<AbilitiesOfAbility> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<AbilitiesOfAbility> abilities) {
        this.abilities = abilities;
    }

    public Sprite getSprites() {
        return sprites;
    }

    public void setSprites(Sprite sprites) {
        this.sprites = sprites;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

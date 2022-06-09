package com.pokeapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PokemonDetail {

    private Integer id;
    private String height;
    private String weight;
    private String name;
    private String baseExperience;
    private List <PokemonTypes> types;
    private List <AttributesAbility> abilities;
    private PokemonSprites sprites;

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

    public List<PokemonTypes> getTypes() {
        return types;
    }

    public void setTypes(List<PokemonTypes> types) {
        this.types = types;
    }

    public List<AttributesAbility> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<AttributesAbility> abilities) {
        this.abilities = abilities;
    }

    public PokemonSprites getSprites() {
        return sprites;
    }

    public void setSprites(PokemonSprites sprites) {
        this.sprites = sprites;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

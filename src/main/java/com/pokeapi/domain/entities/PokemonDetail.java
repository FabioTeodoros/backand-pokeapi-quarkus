package com.pokeapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class PokemonDetail {

    private String id;
    private String height;
    private String weight;
    private String name;
    private String baseExperience;
    private List <TypeOfType> types;
    private List <AbilitiesOfAbility> abilities;
    private Sprites sprites;

    public PokemonDetail() {
    }

    public PokemonDetail(String name) {
        this.name = name;
    }

    public PokemonDetail(String id
            , String height
            , String weight
            , String name
            , String baseExperience
            , List <TypeOfType> types
            , List<AbilitiesOfAbility> abilities
            , Sprites sprites) {
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.name = name;
        this.baseExperience = baseExperience;
        this.types = types;
        this.abilities = abilities;
        this.sprites = sprites;
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

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PokemonDetail)) {
            return false;
        }

        PokemonDetail other = (PokemonDetail) obj;

        return Objects.equals(other.name, this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}

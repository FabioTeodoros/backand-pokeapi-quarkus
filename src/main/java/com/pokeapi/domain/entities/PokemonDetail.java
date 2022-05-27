package com.pokeapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class PokemonDetail {

   // private String id;
   // private Integer height;
   // private Integer weight;
    private String name;
   // private String baseExperience;
   // private List<TypesOfType> types;
   // private List<AbilitiesOfAbility> abilities;
   // private Sprites sprites;

    public PokemonDetail() {
    }

    /*public PokemonDetail(String id
            , Integer height
            , Integer weight
            , String name
            , String baseExperience
            , List<TypesOfType> types
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

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
/*
    @JsonProperty("base_experience")
    public String getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(String baseExperience) {
        this.baseExperience = baseExperience;
    }

    public List<TypesOfType> getTypes() {
        return types;
    }

    public void setTypes(List<TypesOfType> types) {
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

 */
}

package com.pokeapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PokemonFullContract {

    private String id;
    private String model;
    private String name;
    private String urlImage;

    public PokemonFullContract() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("url_image")
    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}

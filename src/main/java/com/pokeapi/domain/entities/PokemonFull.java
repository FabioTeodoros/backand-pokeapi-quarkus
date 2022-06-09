package com.pokeapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PokemonFull {

    private Integer id;
    private String model;
    private String name;
    private String urlImage;

    public PokemonFull() {
        this.id = null;
        this.model = null;
        this.name = null;
        this.urlImage = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

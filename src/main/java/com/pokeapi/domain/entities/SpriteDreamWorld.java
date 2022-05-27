package com.pokeapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpriteDreamWorld {

    private SpriteFrontDefault spriteFrontDefault;

    @JsonProperty("dream_world")
    public SpriteFrontDefault getFrontDefault() {
        return spriteFrontDefault;
    }

    public void setFrontDefault(SpriteFrontDefault spriteFrontDefault) {
        this.spriteFrontDefault = spriteFrontDefault;
    }
}

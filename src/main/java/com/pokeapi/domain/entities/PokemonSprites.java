package com.pokeapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PokemonSprites {

    private String spriteFrontDefault;
    private AttributesSpriteBigPicture attributesSpriteBigPicture;

    @JsonProperty("other")
    public AttributesSpriteBigPicture getSpriteDreamWorld() {
        return attributesSpriteBigPicture;
    }

    public void setSpriteDreamWorld(AttributesSpriteBigPicture attributesSpriteBigPicture) {
        this.attributesSpriteBigPicture = attributesSpriteBigPicture;
    }

    @JsonProperty("front_default")
    public String getSpriteFrontDefault() {
        return spriteFrontDefault;
    }

    public void setSpriteFrontDefault(String spriteFrontDefault) {
        this.spriteFrontDefault = spriteFrontDefault;
    }

}


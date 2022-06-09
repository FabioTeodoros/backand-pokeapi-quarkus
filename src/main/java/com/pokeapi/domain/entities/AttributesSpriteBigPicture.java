package com.pokeapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AttributesSpriteBigPicture {

    private AttributesSpriteSmallPicture attributesSpriteSmallPicture;

    @JsonProperty("dream_world")
    public AttributesSpriteSmallPicture getFrontDefault() {
        return attributesSpriteSmallPicture;
    }

    public void setFrontDefault(AttributesSpriteSmallPicture attributesSpriteSmallPicture) {
        this.attributesSpriteSmallPicture = attributesSpriteSmallPicture;
    }
}

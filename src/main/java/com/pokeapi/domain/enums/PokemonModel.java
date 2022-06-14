package com.pokeapi.domain.enums;

public enum PokemonModel {
    PERSONAL(1,"personal"),
    OFFICIAL(2,"official"),
    ALL(2,"all");

    private final Integer value;
    private final String descriptor;

    PokemonModel(Integer value, String descriptor) {
        this.value = value;
        this.descriptor = descriptor;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescriptor() {
        return descriptor;
    }
}

package com.pokeapi.enums;

public enum PokemonModel {
    PERSONAL(1, "personmal"),
    OFFICIAL(2, "official");

    private final int valor;
    private final String descricao;

    PokemonModel(int valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public int getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }
}

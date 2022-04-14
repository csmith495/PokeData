package com.example.pokedata.model

data class PokemonList (
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<PokemonListItem>
)
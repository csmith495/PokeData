package com.example.pokedata.api


import com.example.pokedata.model.PokemonList
import com.example.pokedata.model.PokemonModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApi {

    @GET("v2/pokemon/?limit=1126")
    fun getAllPokemon(): Call<PokemonList>

    @GET("v2/pokemon/{name}")
    fun getPokemon(@Path("name") name: String): Call<PokemonModel>
}
package com.example.pokedata.api

import android.util.Log
import com.example.pokedata.PokemonListViewModel
import com.example.pokedata.model.PokemonList
import com.example.pokedata.model.PokemonListItem
import com.example.pokedata.model.PokemonModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "PokeFetcher"

class PokeFetcher {

    private val pokeApi: PokeApi
    init {
        val retrofit: Retrofit = Retrofit
            .Builder()
            .baseUrl("https://pokeapi.co/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        pokeApi = retrofit.create(PokeApi::class.java)
    }

    // load all pokemon, call function with it
    fun getAllPokemon(func: (input: List<PokemonListItem>) -> Unit) {
        val request: Call<PokemonList> = pokeApi.getAllPokemon()
        request.enqueue(object: Callback<PokemonList> {
            override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
                Log.d(TAG, "Response Received: ${response.body().toString()}")

                val pokemonList: PokemonList? = response.body()
                val allPokemon: List<PokemonListItem>? = pokemonList?.results
                if (allPokemon != null) {
                    func(allPokemon)
                }
            }

            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                Log.e(TAG, "Failed to fetch pokemon data: ${t.message}")
            }

        })
    }

    // load a single pokemon
    fun getPokemon(name: String,
                   onResponse: (input: PokemonModel) -> Unit,
                   onFailure: () -> Unit) {
        Log.d("Getting pokemon", name)
        val request: Call<PokemonModel> = pokeApi.getPokemon(name)
        request.enqueue(object: Callback<PokemonModel>{
            override fun onResponse(call: Call<PokemonModel>, response: Response<PokemonModel>) {
                Log.d(TAG, "Response Received: ${response.body().toString()}")

                val pokemon: PokemonModel? = response.body()
                if (pokemon != null) {
                    onResponse(pokemon)
                }
            }

            override fun onFailure(call: Call<PokemonModel>, t: Throwable) {
                Log.e(TAG, "Failed to fetch pokemon data: ${t.message}")
                onFailure()
            }
        })
    }

}
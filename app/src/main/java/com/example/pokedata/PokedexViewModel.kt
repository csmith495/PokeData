package com.example.pokedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedata.model.PokemonListItem
import com.example.pokedata.model.PokemonModel

class PokedexViewModel: ViewModel()  {
    var pokedexLiveData = MutableLiveData<PokemonModel>()
}
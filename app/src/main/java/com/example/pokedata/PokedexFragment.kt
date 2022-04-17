package com.example.pokedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.pokedata.model.PokemonModel
import com.squareup.picasso.Picasso


// PokédexFragment shows the data of an individual pokemon
class PokedexFragment(pokedexViewModel: PokedexViewModel): Fragment() {
    var pokedex: PokedexViewModel = pokedexViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.pokedex_fragment, container, false)

        pokedex.pokedexLiveData.observe(viewLifecycleOwner, Observer { pokemon ->

            val tvName = requireView().findViewById<TextView>(R.id.tv_pokedexName)
            val tvWeight = requireView().findViewById<TextView>(R.id.tv_pokedexWeight)
            val pokemonImg = requireView().findViewById<ImageView>(R.id.img_pokedexPokemon)

            val sprURL = pokemon.sprites.frontDefault
            Picasso.get().load(sprURL).into(pokemonImg)
            tvName.text = pokemon.name
            val weight = pokemon.weight
            tvWeight.text = "Weight: $weight"
        })

        return view
    }

}
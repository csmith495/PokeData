package com.example.pokedata

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


// shows the full list of pokemon
class PokemonListFragment(pokemonListViewModel: PokemonListViewModel): Fragment() {
    private lateinit var listAdapter: ListAdapter
    private val pokeListViewModel: PokemonListViewModel = pokemonListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.pokemon_list_fragment, container, false)

        listAdapter = ListAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_pokelist)
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        pokeListViewModel.pokemonLiveData.observe(viewLifecycleOwner, Observer { pokemonList ->
            listAdapter.setData(pokemonList)
            recyclerView.scrollToPosition(0)
        })

        return view
    }

}
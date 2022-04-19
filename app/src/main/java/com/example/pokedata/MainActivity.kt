package com.example.pokedata

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedata.model.PokemonModel

class MainActivity : AppCompatActivity() {

    lateinit var pokelistViewModel: PokemonListViewModel
    lateinit var pokedex: PokedexViewModel
    private lateinit var pokelistFragment: PokemonListFragment
    private lateinit var pokedexFragment: PokedexFragment

    private fun initMembers() {
        pokelistViewModel = PokemonListViewModel()
        pokedex = PokedexViewModel()
        pokelistFragment = PokemonListFragment(pokelistViewModel)
        pokedexFragment = PokedexFragment(pokedex)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMembers()

        pokelistViewModel.loadAllPokemon()

        gotoList()

        val searchTextInput = findViewById<EditText>(R.id.et_searchPokemon)

        findViewById<ImageButton>(R.id.btn_searchPokemon).setOnClickListener {
            gotoList()
            val searchText = searchTextInput.text
            if (searchText.isNotEmpty()) {
                if (pokelistViewModel.pokemonLiveData.value != null) {
                    val n = pokelistViewModel.pokemonLiveData.value!!.size
                    Log.d("size", "$n")
                }
                pokelistViewModel.sortBySearchTerm(searchText.toString())
            }
        }

    }

    fun gotoList() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFrameLayout, pokelistFragment)
            commit()
        }
    }

    fun gotoPokedex(pokemon: PokemonModel) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFrameLayout, pokedexFragment)
            commit()
        }
        pokedex.pokedexLiveData.value = pokemon
    }


}
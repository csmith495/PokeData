package com.example.pokedata

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.pokedata.model.PokemonModel

class MainActivity : AppCompatActivity() {

    lateinit var pokelistViewModel: PokemonListViewModel
    lateinit var pokedex: PokedexViewModel
    private lateinit var pokelistFragment: PokemonListFragment
    private lateinit var pokedexFragment: PokedexFragment
    private var pokemonPreloader: PokemonPreloader? = null

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

        pokelistViewModel.pokemonLiveData.observe(this, Observer { pokemonList ->
            if (pokemonPreloader == null) {
                val namesList: List<String> = pokemonList.map { it.name }
                pokemonPreloader = PokemonPreloader(namesList, this)
                pokemonPreloader!!.start()
            }
        })

        gotoList()

        val searchTextInput = findViewById<EditText>(R.id.et_searchPokemon)

        findViewById<ImageButton>(R.id.btn_searchPokemon).setOnClickListener {
            gotoList()
            val searchText = searchTextInput.text
            if (searchText.isNotEmpty()) {
                pokelistViewModel.sortBySearchTerm(searchText.toString())
                searchTextInput.text.clear()
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

    fun getModel(name: String): PokemonModel? {
        if (pokemonPreloader != null) {
            Log.d("Num loaded", pokemonPreloader!!.modelBuffer.size.toString())
            return pokemonPreloader!!.modelBuffer[name]
        }
        return null
    }

    fun addModel(name: String, model: PokemonModel) {
        if (pokemonPreloader != null) {
            pokemonPreloader!!.modelBuffer[name] = model
        }
    }


}
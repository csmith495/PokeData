package com.example.pokedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokedata.api.PokeFetcher

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pokeFetcher: PokeFetcher = PokeFetcher()
        pokeFetcher.getAllPokemon()
        pokeFetcher.getPokemon("pikachu")
    }
}
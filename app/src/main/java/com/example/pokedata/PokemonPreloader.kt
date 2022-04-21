package com.example.pokedata

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pokedata.api.PokeFetcher
import com.example.pokedata.model.PokemonModel
import java.util.Collections.synchronizedMap
import java.util.concurrent.ConcurrentLinkedQueue

class PokemonPreloader(namesList: List<String>, val context: Context): Thread() {

    private var busy: Boolean = false
    private var setNotBusy: Boolean = false
    private var running: Boolean = true
    private var pokemonQueue: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
    val modelBuffer = synchronizedMap(mutableMapOf<String, PokemonModel>())
    init {
        for (name in namesList) {
            pokemonQueue.add(name)
        }
    }

    override fun run() {
        super.run()
        Thread.sleep(DELAY_START_TIME)

        while (running) {
            if (!busy) {
                if (pokemonQueue.isNotEmpty()) {
                    // if there are pokemon left, retrieve them
                    val pokeFetcher = PokeFetcher()
                    val name = pokemonQueue.poll()
                    // if you haven't already gotten it
                    if (modelBuffer[name] == null) {
                        busy = true
                        pokeFetcher.getPokemon(name)
                        { pokemonModel ->
                            // preload the image
                            Glide.with(context)
                                .load(pokemonModel.sprites.frontDefault)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .preload()
                            // add it to the stored pokemon models
                            modelBuffer[name] = pokemonModel
                            setNotBusy = true
                        }
                    }
                }
                else {
                    // if nothing else to do, exit the loop
                    running = false
                }
            }
            else if (setNotBusy) {
                /*  when finished getting this pokemon,
                    wait a little while before continuing.*/
                setNotBusy = false
                busy = false
                Thread.sleep(Companion.WAIT_TIME)
            }

        }

    }

    companion object {
        private const val DELAY_START_TIME: Long = 5000L
        private const val WAIT_TIME: Long = 1L
    }

}

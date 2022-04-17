package com.example.pokedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedata.api.PokeFetcher
import com.example.pokedata.model.PokemonListItem


class PokemonListViewModel: ViewModel() {
    var pokemonLiveData = MutableLiveData<List<PokemonListItem>>()

    fun loadAllPokemon() {
        val pokeFetcher = PokeFetcher()
        pokeFetcher.getAllPokemon { allPokemon ->
            pokemonLiveData.value = allPokemon
        }
    }

    // similarity of two strings based on hamming distance
    private fun hammingSimilarity(str1: String, str2: String): Double {
        val n = str1.length.coerceAtMost(str2.length)
        var hammingDistTotal: Int = 0
        for (i in 0 until n) {
            if (str1[i] == str2[i]) {
                hammingDistTotal++
            }
        }
        val maxLength = str1.length.coerceAtMost(str2.length).toDouble()
        return hammingDistTotal.toDouble() / maxLength
    }

    // similarity of two strings based on levenshtein distance
    private fun levenshteinSimilarity(str1: String, str2: String): Double {
        val n = str1.length
        val m = str2.length
        val T = Array(n + 1) { IntArray(m + 1) }
        for (i in 1..n) {
            T[i][0] = i
        }
        for (j in 1..m) {
            T[0][j] = j
        }
        var cost: Int
        for (i in 1..n) {
            for (j in 1..m) {
                cost = if (str1[i - 1] == str2[j - 1]) 0 else 1
                T[i][j] = Integer.min(
                    Integer.min(T[i - 1][j] + 1, T[i][j - 1] + 1),
                    T[i - 1][j - 1] + cost
                )
            }
        }
        val ld: Double = T[n][m].toDouble()

        val maxLength = str1.length.coerceAtLeast(str2.length).toDouble()
        return if (maxLength > 0) {
            (maxLength - ld) / maxLength
        } else 1.0
    }

    // use various measures of similarity to decide how similar the strings are
    private fun similarity(searchTerm: String, pokemonName: String): Double {
        val sim1 = hammingSimilarity(pokemonName, searchTerm)
        val sim2 = levenshteinSimilarity(pokemonName, searchTerm)
        val sim3 = if (pokemonName.contains(searchTerm, true))
            1.0 else 0.0
        return sim1 + sim2 + sim3
    }

    // sort the contents of the list based on how similar
    // each pokemon's name is to the search term provided
    fun sortBySearchTerm(searchTerm: String) {
        if (pokemonLiveData.value != null) {
            val pl = pokemonLiveData.value!!
            pokemonLiveData.value = pl.sortedByDescending {
                similarity(searchTerm, it.name)
            }
        }
    }

}

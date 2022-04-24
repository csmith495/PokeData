package com.example.pokedata

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedata.api.PokeFetcher
import com.example.pokedata.model.PokemonListItem
import com.example.pokedata.model.PokemonModel


class ListAdapter: RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var pokeList: List<PokemonListItem> = emptyList<PokemonListItem>()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return pokeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = pokeList[position].name
        val mainActivity = holder.itemView.context as MainActivity

        // set name early so there is no delay when showing this list item
        holder.itemView.findViewById<TextView>(R.id.li_name).text = name

        val recievedModel = mainActivity.getModel(name)
        if (recievedModel != null) {
            val pokemon = recievedModel!!
            displayPokemonModel(pokemon, holder, mainActivity)
        }
        else {
            val pokeFetcher = PokeFetcher()
            pokeFetcher.getPokemon(name, { pokemon ->
                displayPokemonModel(pokemon, holder, mainActivity)
                mainActivity.addModel(name, pokemon)
            }, {})
        }

    }

    private fun displayPokemonModel(pokemon: PokemonModel, holder: ViewHolder, mainActivity: MainActivity) {
        val itemView = holder.itemView
        val tvName = itemView.findViewById<TextView>(R.id.li_name)
        val pokemonImg = itemView.findViewById<ImageView>(R.id.img_pokemon)
        val miniStats = itemView.findViewById<TextView>(R.id.stats_pokemon)

        tvName.text = pokemon.name
        val pokT = pokemon.types
        val pt1 = pokT[0].type.name
        if (pokT.size > 1){
            val pt2 = pokT[1].type.name
            miniStats.text = "Types: $pt1 and $pt2"
        }
        else{
            miniStats.text = "Types: $pt1"
        }

        holder.itemView.setOnClickListener {
            mainActivity.gotoPokedex(pokemon)
        }

        val sprURL = pokemon.sprites.frontDefault

        Glide.with(itemView.context)
            .asBitmap()
            .load(sprURL).into(pokemonImg)

    }


    fun setData(data: List<PokemonListItem>) {
        this.pokeList = data
        notifyDataSetChanged()
    }

}
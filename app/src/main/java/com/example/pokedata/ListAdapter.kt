package com.example.pokedata

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedata.api.PokeFetcher
import com.example.pokedata.model.PokemonListItem
import com.example.pokedata.model.PokemonModel
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.net.URL
import java.util.*


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
        // init
        val name = pokeList[position].name
        var itemView = holder.itemView

        // get views
        val tvName = itemView.findViewById<TextView>(R.id.li_name)
        val pokemonImg = itemView.findViewById<ImageView>(R.id.img_pokemon)

        // ________________
        // RecyclerView logic

        tvName.text = name

        val pokeFetcher = PokeFetcher()
        pokeFetcher.getPokemon(name) { pokemon ->

            holder.itemView.setOnClickListener {
                try {
                    val activity = itemView.context as MainActivity
                    activity.gotoPokedex(pokemon)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            val sprURL = pokemon.sprites.frontDefault
            Picasso.get().load(sprURL).into(pokemonImg)
        }

    }


    fun setData(data: List<PokemonListItem>) {
        this.pokeList = data
        notifyDataSetChanged()
    }

}
package com.example.pokedata

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
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
import java.lang.Exception
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
        val activity = itemView.context as MainActivity

        // get views
        val tvName = itemView.findViewById<TextView>(R.id.li_name)
        val pokemonImg = itemView.findViewById<ImageView>(R.id.img_pokemon)

        // ________________
        // RecyclerView logic

        tvName.text = name

        val pokeFetcher = PokeFetcher()
        pokeFetcher.getPokemon(name) { pokemon ->

            holder.itemView.setOnClickListener {
                activity.gotoPokedex(pokemon)
            }

            val sprURL = pokemon.sprites.frontDefault

            Glide.with(itemView.context)
                .asBitmap()
                .load(sprURL).into(pokemonImg)

        }

    }


    fun setData(data: List<PokemonListItem>) {
        this.pokeList = data
        notifyDataSetChanged()
    }

}
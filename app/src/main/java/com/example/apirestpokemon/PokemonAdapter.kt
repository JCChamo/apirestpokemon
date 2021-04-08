package com.example.apirestpokemon

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apirestpokemon.models.Pokemon

class PokemonAdapter() : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    var pokemonList : ArrayList<Pokemon> = ArrayList()

    class ViewHolder (view : View) : RecyclerView.ViewHolder(view){
        val sprite : ImageView = view.findViewById(R.id.sprite)
        val pokemonName : TextView = view.findViewById(R.id.name)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var pokemon : Pokemon = pokemonList[position]
        holder.pokemonName.text = pokemon.getName()
    }

    override fun getItemCount() = pokemonList.size

    fun addPokemon(pokemonList: ArrayList<Pokemon>) {
        pokemonList.addAll(pokemonList)
        notifyDataSetChanged()
        Log.d(":::TAG", pokemonList[0].getName())
    }
}
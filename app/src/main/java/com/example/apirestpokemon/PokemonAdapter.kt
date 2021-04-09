package com.example.apirestpokemon

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apirestpokemon.models.Pokemon
import com.squareup.picasso.Picasso

class PokemonAdapter() : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    private lateinit var pokemonList : ArrayList<Pokemon>

    class ViewHolder (view : View) : RecyclerView.ViewHolder(view){
        val sprite : ImageView = view.findViewById(R.id.sprite)
        val pokemonName : TextView = view.findViewById(R.id.name)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = pokemonList.size

    override fun onBindViewHolder(holder: PokemonAdapter.ViewHolder, position: Int) {
        val pokemon : Pokemon = pokemonList[position]
        holder.pokemonName.text = pokemon.getName()
        holder.sprite.fromUrl(pokemon)
    }

    fun setData(pokemonList : ArrayList<Pokemon> ) {
        this.pokemonList = pokemonList;
        notifyDataSetChanged();
    }

    private fun ImageView.fromUrl(pokemon : Pokemon){
        var url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
        Picasso.get().load(url + pokemon.getNumber() + ".png").into(this)
    }
}
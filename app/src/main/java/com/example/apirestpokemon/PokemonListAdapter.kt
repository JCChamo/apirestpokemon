package com.example.apirestpokemon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apirestpokemon.models.Pokemon
import com.squareup.picasso.Picasso

class PokemonListAdapter(var listener: OnItemClickListener) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {
    private lateinit var pokemonList : ArrayList<Pokemon>

    inner class ViewHolder (view : View) : RecyclerView.ViewHolder(view), View.OnClickListener{

        val sprite : ImageView = view.findViewById(R.id.sprite)
        val pokemonName : TextView = view.findViewById(R.id.name)


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?){
             val position = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                listener.onItemClick(position)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = pokemonList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon : Pokemon = pokemonList[position]
        holder.pokemonName.text = pokemon.getName()
        holder.sprite.fromUrl(pokemon)
    }

    fun setData(pokemonList : ArrayList<Pokemon> ) {
        this.pokemonList = pokemonList
        notifyDataSetChanged()
    }

    private fun ImageView.fromUrl(pokemon : Pokemon){
        var url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
        Picasso.get().load(url + pokemon.getNumber() + ".png").into(this)
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}
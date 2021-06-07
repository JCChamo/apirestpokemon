package com.example.apirestpokemon

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apirestpokemon.models.Pokemon
import com.example.apirestpokemon.models.PokemonListViewModel

class MainActivity : AppCompatActivity(), PokemonListAdapter.OnItemClickListener, SearchView.OnQueryTextListener {

    lateinit var recyclerView : RecyclerView
    lateinit var searchView : SearchView
    lateinit var pokemonAdapter : PokemonListAdapter
    private lateinit var pokemonListViewModel : PokemonListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);


        pokemonListViewModel = ViewModelProvider(this).get(PokemonListViewModel::class.java)

        pokemonAdapter = PokemonListAdapter(this)
        recyclerView = findViewById(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchView = findViewById(R.id.search)
        searchView.setOnQueryTextListener(this)

        getData()

    }

    private fun getData(){
        recyclerView.adapter = pokemonAdapter
        pokemonListViewModel.getPokemonList()

        pokemonListViewModel.pokemonList.observe(this, Observer { list ->
            (recyclerView.adapter as PokemonListAdapter).setData(list.results)
        })
        pokemonAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("id", position)
        startActivity(intent)
    }

    override fun onQueryTextSubmit(newText: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        filter(newText!!)
        return false
    }

    private fun filter(text : String) {
        var updatedPokemonList = arrayListOf<Pokemon>()
        var pokemonList = pokemonAdapter.getPokemonList()

        for (i in 0 until pokemonList.size){
            val pokemon = pokemonList[i]
            if (pokemon.getName().startsWith(text)) {
                updatedPokemonList.add(pokemon)
            }
            pokemonAdapter.filterList(updatedPokemonList)
        }
    }
}
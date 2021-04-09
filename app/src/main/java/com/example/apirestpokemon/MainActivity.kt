package com.example.apirestpokemon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apirestpokemon.interfaces.PokemonListService
import com.example.apirestpokemon.models.Pokemon
import com.example.apirestpokemon.models.PokemonListResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), PokemonListAdapter.OnItemClickListener, SearchView.OnQueryTextListener {

    lateinit var retrofit : Retrofit
    lateinit var recyclerView : RecyclerView
    lateinit var searchView : SearchView
    lateinit var pokemonAdapter : PokemonListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        getData()

        pokemonAdapter = PokemonListAdapter(this)
        recyclerView = findViewById(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchView = findViewById(R.id.search)
        searchView.setOnQueryTextListener(this)

    }

    private fun getData(){
        doAsync {
            val service = retrofit.create(PokemonListService :: class.java)
            val call : Call<PokemonListResponse> = service.getPokemonList(150, 0)
            call.enqueue(object : Callback<PokemonListResponse>{
                override fun onResponse(
                        call: Call<PokemonListResponse>,
                        listResponse: Response<PokemonListResponse>
                ) {
                    if (listResponse.isSuccessful){
                        val pokemonListResponse : PokemonListResponse = listResponse.body()!!
                        pokemonAdapter.setData(pokemonListResponse.results)
                        recyclerView.adapter = pokemonAdapter
                        pokemonAdapter.notifyDataSetChanged()
                    } else
                        Log.e(":::TAG", "ON RESPONSE: FALLO AL ENCONTRAR LA RESPUESTA")
                }
                override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                    Log.e(":::TAG", "ON FAILURE: FALLO AL ENCONTRAR LA RESPUESTA")
                }
            })
        }
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
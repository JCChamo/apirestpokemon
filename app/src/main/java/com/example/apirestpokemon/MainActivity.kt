package com.example.apirestpokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var search : SearchView
    private lateinit var recycler : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search = findViewById(R.id.search)
        recycler = findViewById(R.id.recycler)

        search.setOnQueryTextListener(this)

    }

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/pokemon/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(query: String){
        doAsync {
            val call = getRetrofit().create(ApiService :: class.java).getPokemonByName("$query").execute()
            val body = call.body() as PokemonResponse
            uiThread {
                initCharacter(body)
                hideKeyboard()
            }
        }
    }

    private fun initCharacter(body: PokemonResponse) {

    }

    private fun hideKeyboard() {
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        searchByName(newText.toLowerCase())
        return true
    }
}
package com.example.apirestpokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apirestpokemon.interfaces.ApiService
import com.example.apirestpokemon.models.Pokemon
import com.example.apirestpokemon.models.PokemonResponse
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var retrofit : Retrofit
    lateinit var recyclerView : RecyclerView
    lateinit var searchView : SearchView
    lateinit var pokemonAdapter : PokemonAdapter
    var pokemonList : ArrayList<Pokemon> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler)
        pokemonAdapter = PokemonAdapter()
        recyclerView.adapter = pokemonAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        getData()
    }

    fun getData(){
        doAsync {
            var service = retrofit.create(ApiService :: class.java)
            var response : Call<PokemonResponse> = service.getPokemonList()
            response.enqueue(object : Callback<PokemonResponse>{
                override fun onResponse(
                    call: Call<PokemonResponse>,
                    response: Response<PokemonResponse>
                ) {
                    if (response.isSuccessful){
                        var pokemonResponse : PokemonResponse = response.body()!!
                        pokemonList = pokemonResponse.results

                        Log.d(":::TAG", "" + pokemonList.size)
                        pokemonAdapter.addPokemon(pokemonList)
                    } else
                        Log.e(":::TAG", "ON RESPONSE: FALLO AL ENCONTRAR LA RESPUESTA")
                }
                override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                    Log.e(":::TAG", "ON FAILURE: FALLO AL ENCONTRAR LA RESPUESTA")
                }
            })
        }
    }
}
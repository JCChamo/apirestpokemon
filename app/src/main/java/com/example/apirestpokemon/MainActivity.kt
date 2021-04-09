package com.example.apirestpokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
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
    var pokemonList = arrayListOf<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        getData()

        pokemonAdapter = PokemonAdapter()
        recyclerView = findViewById(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun getData(){
        doAsync {
            val service = retrofit.create(ApiService :: class.java)
            val call : Call<PokemonResponse> = service.getPokemonList(150, 0)
            call.enqueue(object : Callback<PokemonResponse>{
                override fun onResponse(
                    call: Call<PokemonResponse>,
                    response: Response<PokemonResponse>
                ) {
                    if (response.isSuccessful){
                        val pokemonResponse : PokemonResponse = response.body()!!
                        pokemonAdapter.setData(pokemonResponse.results)
                        recyclerView.adapter = pokemonAdapter
                        pokemonAdapter.notifyDataSetChanged()
//                        pokemonList = pokemonResponse.results
//                        Log.d(":::", pokemonList[0].getName())
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
package com.example.apirestpokemon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apirestpokemon.interfaces.ApiService
import com.example.apirestpokemon.models.PokemonListResponse
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), PokemonListAdapter.OnItemClickListener {

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

    }

    private fun getData(){
        doAsync {
            val service = retrofit.create(ApiService :: class.java)
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
//        toast("hola")
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("id", position)
        Log.d(":::", position.toString())
        startActivity(intent)
    }
}
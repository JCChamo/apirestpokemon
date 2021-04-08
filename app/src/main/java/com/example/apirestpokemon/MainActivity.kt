package com.example.apirestpokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.apirestpokemon.databinding.ActivityMainBinding
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

    private lateinit var binding: ActivityMainBinding
    lateinit var retrofit : Retrofit


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        getData()
    }

    fun getData(){
        doAsync {
            Log.d(":::TAG", "DENTRO DOASYNC")
            var service : ApiService = retrofit.create(ApiService :: class.java)
            //fallo a partir de aquí
            var response : Call<PokemonResponse> = service.getPokemonList()
            response.enqueue(object : Callback<PokemonResponse>{
                override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                    Log.e(":::TAG", "ON FAILURE: FALLO AL ENCONTRAR LA RESPUESTA")
                }

                override fun onResponse(
                    call: Call<PokemonResponse>,
                    response: Response<PokemonResponse>
                ) {
                    if (response.isSuccessful){
                        var pokemonResponse : PokemonResponse = response.body()!!
                        var pokemonList : ArrayList<Pokemon> = pokemonResponse.results

                        for (i in 0 until pokemonList.size){
                            var pokemon : Pokemon = pokemonList[i]
                            Log.d(":::TAG", pokemon.getName())
                        }
                    } else
                        Log.e(":::TAG", "ON RESPONSE: FALLO AL ENCONTRAR LA RESPUESTA")
                }
            })
        }
    }
}
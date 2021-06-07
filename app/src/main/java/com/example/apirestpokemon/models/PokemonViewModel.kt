package com.example.apirestpokemon.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apirestpokemon.interfaces.DetailService
import com.example.apirestpokemon.interfaces.PokemonListService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonViewModel: ViewModel() {

    private var retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(DetailService ::class.java)

    val pokemonInfo = MutableLiveData<DetailResponse>()

    fun getPokemonInfo(pokemonId : String) {
        val call : Call<DetailResponse> = service.getPokemonDetail(pokemonId)
        call.enqueue(object : Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                response.body()?.let { pokemon ->
                    pokemonInfo.postValue(pokemon)
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                call.cancel()
                Log.e(":::TAG", "ON FAILURE: FALLO AL ENCONTRAR LA RESPUESTA")
            }
        })
    }
}
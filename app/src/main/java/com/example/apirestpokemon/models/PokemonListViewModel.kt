package com.example.apirestpokemon.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apirestpokemon.interfaces.PokemonListService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonListViewModel: ViewModel() {

    private var retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(PokemonListService :: class.java)

    val pokemonList = MutableLiveData<PokemonListResponse>()

    fun getPokemonList() {
        val call : Call<PokemonListResponse> = service.getPokemonList(150, 0)
        call.enqueue(object : Callback<PokemonListResponse> {
            override fun onResponse(
                call: Call<PokemonListResponse>,
                listResponse: Response<PokemonListResponse>
            ) {
                listResponse.body()?.let {
                    list -> pokemonList.postValue(list)
                }
            }
            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                call.cancel()
                Log.e(":::TAG", "ON RESPONSE: FALLO AL ENCONTRAR LA RESPUESTA")
            }
        })
    }
}
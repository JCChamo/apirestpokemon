package com.example.apirestpokemon.interfaces

import com.example.apirestpokemon.models.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("pokemon")
    fun getPokemonList() : Call<PokemonResponse>
}
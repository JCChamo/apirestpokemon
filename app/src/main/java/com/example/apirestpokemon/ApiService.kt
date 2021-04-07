package com.example.apirestpokemon

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    fun getPokemonByName(@Url url: String) : Call<PokemonResponse>
}
package com.example.apirestpokemon.interfaces

import com.example.apirestpokemon.models.DetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {

    @GET("pokemon/{id}")
    fun getPokemonDetail(@Path("id") id: String) : Call<DetailResponse>
}
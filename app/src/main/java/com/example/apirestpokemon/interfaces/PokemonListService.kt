package com.example.apirestpokemon.interfaces

import com.example.apirestpokemon.models.PokemonListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonListService {

    @GET("pokemon")
    fun getPokemonList(@Query("limit") limit : Int, @Query("offset") offset : Int) : Call<PokemonListResponse>
}
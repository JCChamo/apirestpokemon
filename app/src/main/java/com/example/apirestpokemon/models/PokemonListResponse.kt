package com.example.apirestpokemon.models

import com.google.gson.annotations.SerializedName

data class PokemonListResponse (
    @SerializedName("results") var results: ArrayList<Pokemon>
)
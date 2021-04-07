package com.example.apirestpokemon

import com.google.gson.annotations.SerializedName

data class PokemonResponse (
    @SerializedName("name") var pokemonName: String,
    @SerializedName("url") var url: String
)
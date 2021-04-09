package com.example.apirestpokemon.models

import com.google.gson.annotations.SerializedName

data class DetailResponse (
    @SerializedName("species") var species : Species,
    @SerializedName("types") var types : ArrayList<Types>
)
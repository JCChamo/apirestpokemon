package com.example.apirestpokemon.models

import android.util.Log

class Pokemon {
    private var name : String
    private var url : String

    constructor(
        name : String,
        url : String
    ) {
        this.name = name
        this.url = url
    }

    fun getName() : String {
        return name
    }

    fun getUrl() : String {
        return url
    }

    fun getNumber() : Int {
        val spriteBaseUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
        val urlTokens = url.split("/")
        return Integer.parseInt(urlTokens[urlTokens.size - 2])
    }

    fun getArtworkUrl() : String {
        val baseUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"
        return baseUrl + getNumber() + ".png"
    }
}
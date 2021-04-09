package com.example.apirestpokemon.models

import android.util.Log

class Pokemon(private var name: String, private var url: String) {

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
}
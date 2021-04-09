package com.example.apirestpokemon.models

class Pokemon {
    private var name : String
    private var number : Int = 0
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
        return number
    }
}
package com.example.apirestpokemon.models

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
}
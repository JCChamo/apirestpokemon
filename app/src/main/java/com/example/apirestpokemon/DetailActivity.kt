package com.example.apirestpokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : AppCompatActivity(){

    lateinit var pokemonName : TextView
    lateinit var artwork : ImageView
    lateinit var backButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail);

        pokemonName = findViewById(R.id.name2)
        artwork = findViewById(R.id.artwork)
        backButton = findViewById(R.id.backButton)
    }

}
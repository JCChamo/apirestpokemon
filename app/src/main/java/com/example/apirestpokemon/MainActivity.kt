package com.example.apirestpokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.temporal.TemporalQuery

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/pokemon/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(query: String){
        doAsync {
            val call = getRetrofit().create(ApiService :: class.java).getPokemonByName("$query").execute()
            val body = call.body() as PokemonResponse
            uiThread {
                initCharacter(body)
                hideKeyboard()
            }
        }
    }

    private fun initCharacter(body: PokemonResponse) {

    }

    private fun hideKeyboard() {
    }
}
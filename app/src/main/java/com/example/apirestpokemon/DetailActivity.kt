package com.example.apirestpokemon


import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apirestpokemon.models.PokemonViewModel
import com.example.apirestpokemon.models.Types
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity(){

    lateinit var pokemonName : TextView
    lateinit var types : TextView
    lateinit var artwork : ImageView
    lateinit var backButton : Button
    lateinit var layout : LinearLayout
    lateinit var pokemonId : String
    lateinit var pokemonViewModel: PokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail);

        pokemonName = findViewById(R.id.name2)
        types = findViewById(R.id.types)
        artwork = findViewById(R.id.artwork)
        backButton = findViewById(R.id.backButton)
        layout = findViewById(R.id.layout)

        pokemonViewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)

        backButton.setOnClickListener {
            finish()
        }

        pokemonId = (this.intent.extras?.get("id") as Int + 1).toString()
        Picasso.get().load(
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png"
        ).into(artwork)

        getValues(applicationContext)
    }


    private fun getValues(context : Context){

        pokemonViewModel.getPokemonInfo(pokemonId)
        pokemonViewModel.pokemonInfo.observe(this, Observer { pokemon ->
            pokemonName.text = pokemon.species.getName()
            val typesList : ArrayList<Types> = pokemon.types
            for (i in 0 until typesList.size){
                val typesList : Types = typesList[i]
                val type = typesList.getType()
                when (val typeName = type.getName()) {
                    "bug", "dragon", "electric", "fighting", "fire", "flying",
                    "ghost", "grass", "ground", "ice", "normal", "poison", "psychic",
                    "rock", "water" -> {
                        var imageview = ImageView(this@DetailActivity)
                        var uri = "@drawable/${typeName}_type"
                        var id = resources.getIdentifier(uri, null, context.packageName)
                        var imageDrawable = resources.getDrawable(id)
                        imageview.setImageDrawable(imageDrawable)
                        imageview.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
                        layout.addView(imageview)
                    }
                }
            }
        })
    }
}
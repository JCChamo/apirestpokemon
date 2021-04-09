package com.example.apirestpokemon


import android.app.ActionBar
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.apirestpokemon.interfaces.DetailService
import com.example.apirestpokemon.models.DetailResponse
import com.example.apirestpokemon.models.Type
import com.example.apirestpokemon.models.Types
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity(){

    lateinit var pokemonName : TextView
    lateinit var types : TextView
    lateinit var artwork : ImageView
    lateinit var backButton : Button
    lateinit var retrofit : Retrofit
    lateinit var layout : LinearLayout
    lateinit var pokemonId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail);

        pokemonName = findViewById(R.id.name2)
        types = findViewById(R.id.types)
        artwork = findViewById(R.id.artwork)
        backButton = findViewById(R.id.backButton)
        layout = findViewById(R.id.layout)

        retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        backButton.setOnClickListener {
            finish()
        }

        pokemonId = (this.intent.extras?.get("id") as Int + 1).toString()
        Picasso.get().load(
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png"
        ).into(artwork)

        getName()
        getTypes(applicationContext)
    }


    private fun getName(){
        doAsync {
            val service = retrofit.create(DetailService ::class.java)
            val call : Call<DetailResponse> = service.getPokemonDetail(pokemonId)
            call.enqueue(object : Callback<DetailResponse>{
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful){
                        val detailResponse : DetailResponse = response.body()!!
                        pokemonName.text = detailResponse.species.getName()
                    } else
                        Log.e(":::TAG", "ON RESPONSE: FALLO AL ENCONTRAR LA RESPUESTA")
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    Log.e(":::TAG", "ON FAILURE: FALLO AL ENCONTRAR LA RESPUESTA")
                }
            })
        }
    }

    private fun getTypes(context : Context){
        doAsync {
            val service = retrofit.create(DetailService :: class.java)
            val call : Call<DetailResponse> = service.getPokemonDetail(pokemonId)
            call.enqueue(object : Callback<DetailResponse>{
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful){
                        val detailResponse : DetailResponse = response.body()!!
                        val typesList : ArrayList<Types> = detailResponse.types
                        for (i in 0 until typesList.size){
                            val typesList : Types = typesList[i]
                            val type = typesList.getType()
                            val typeName = type.getName()
                            when (typeName) {
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
                    } else
                        Log.e(":::TAG", "ON RESPONSE: FALLO AL ENCONTRAR LA RESPUESTA")
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}
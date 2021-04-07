package com.example.apirestpokemon

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.squareup.picasso.Picasso

class Adapter (var sprite: String) : RecyclerView.Adapter<Adapter.ViewHolder>() {


    class ViewHolder (view : View) : RecyclerView.ViewHolder(view){
        fun bind(sprite: String){
            itemView.sprite.fromUrl(sprite)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    fun ImageView.fromUrl(url:String){
        Picasso.get().load(url).into(this)
    }

}
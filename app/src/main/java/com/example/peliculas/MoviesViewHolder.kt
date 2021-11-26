package com.example.peliculas

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.peliculas.model.Result
import com.squareup.picasso.Picasso

class MoviesViewHolder(view: View): RecyclerView.ViewHolder(view) {

    //id de las vistas de la carta
    private val image: ImageView = itemView.findViewById(R.id.ListImg)
    //funcion para asignar la información a las vistas
    //Usar picasso para las
    fun bind(movie: Result) {
        Picasso.get().load(movie.image.small_url).into(image)
    }
}
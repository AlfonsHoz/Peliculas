package com.example.peliculas.recyclerMoviesList

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.peliculas.R
import com.example.peliculas.model.Result
import com.squareup.picasso.Picasso

class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    //id de las vistas de la carta
    private val image: ImageView = itemView.findViewById(R.id.ListImg)
    private val nameText: TextView = itemView.findViewById(R.id.movieListName)
    private val ratingText: TextView = itemView.findViewById(R.id.rating)
    private val dateText: TextView = itemView.findViewById(R.id.dateText)

    //función para asignar la información a las vistas
    fun bind(movie: Result) {
        Picasso.get().load(movie.image.small_url).into(image)
        nameText.text = movie.name
        ratingText.text = ("Rating: " + movie.rating)
        dateText.text = ("Se agregó el: " + movie.date_added)
    }
}
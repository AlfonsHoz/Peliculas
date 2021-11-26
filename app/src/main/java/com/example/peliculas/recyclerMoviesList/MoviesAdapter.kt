package com.example.peliculas.recyclerMoviesList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.peliculas.R
import com.example.peliculas.model.Result
import com.squareup.picasso.Picasso

class MoviesAdapter(
    val movies: List<Result>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(layoutInflater.inflate(R.layout.movie_item, parent, false), this)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)

    }

    inner class MoviesViewHolder(
        itemView: View,
        moviesAdapter: MoviesAdapter
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        //id de las vistas de la carta
        private val image: ImageView = itemView.findViewById(R.id.listImg)
        private val nameText: TextView = itemView.findViewById(R.id.movieListName)
        private val ratingText: TextView = itemView.findViewById(R.id.rating)
        private val dateText: TextView = itemView.findViewById(R.id.dateText)

        //función para asignar la información a las vistas
        fun bind(movie: Result) {
            Picasso.get().load(movie.image.small_url).into(image)
            nameText.text = movie.name
            ratingText.text = ("Rating: " + movie.rating)
            dateText.text = "Release date: ${movie.release_date}"
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

}


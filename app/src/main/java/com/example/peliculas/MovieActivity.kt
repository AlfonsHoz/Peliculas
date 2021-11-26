package com.example.peliculas

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.peliculas.model.Data
import com.example.peliculas.model.Result
import com.example.peliculas.model.Studio
import com.example.peliculas.webservice.ApiService
import com.example.peliculas.webservice.RetrofitClass
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieActivity : AppCompatActivity() {

    private lateinit var imgMovie: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var rantingMovie: TextView
    private lateinit var studios: TextView
    private lateinit var  description: TextView

    private var id: String = ""
    private lateinit var movie: Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_activity)
        imgMovie = findViewById(R.id.movieImage)
        movieTitle = findViewById(R.id.movieTitle)
        rantingMovie = findViewById(R.id.rantingMovie)
        studios = findViewById(R.id.studios)
        description = findViewById(R.id.description)
        //Trae el id del la película que fue seleccionada
        id = intent.getStringExtra("id").toString()
        //llama a la función para llamar a la api
        getOneMovie()
    }

    override fun onDestroy() {
        super.onDestroy()
        id = ""
    }

    private fun getOneMovie() {
        CoroutineScope(IO).launch {
            val call = RetrofitClass.getRetrofit().create(ApiService::class.java)
            val result: Call<Data> = call.getOneMovieFromApi("id:$id")
            var resp: Data
            result.enqueue(object : Callback<Data> {

                override fun onFailure(call: Call<Data>, t: Throwable) {
                    Log.e("Error Api", t.toString())
                }

                @RequiresApi(Build.VERSION_CODES.N)
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<Data>, response: Response<Data>) {
                    resp = response.body()!!
                    runOnUiThread {
                        if (result.isExecuted) {
                            movie = resp.results[0]
                            updateUi(movie)
                        }
                    }
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateUi(movie: Result) {
        Picasso.get().load(movie.image.medium_url).into(imgMovie)
        movieTitle.text = movie.name
        rantingMovie.text = ("Rating: " + movie.rating)
        studios.text = ("Studios: " + strings(movie.studios))
        description.text = ("Descripción: \n" + Html.fromHtml(movie.description, Html.FROM_HTML_MODE_COMPACT).toString())
    }

    private fun strings(list: List<Studio>): String {
        var res = ""
        list.forEachIndexed { idx, item: Studio ->
            if (idx != list.size) res += (item.name + ", ")
        }
        return res
    }

}
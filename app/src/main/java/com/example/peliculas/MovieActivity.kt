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
import com.example.peliculas.helpers.getJsonDataFromAssets
import com.example.peliculas.helpers.producers
import com.example.peliculas.helpers.strings
import com.example.peliculas.helpers.writers
import com.example.peliculas.model.Data
import com.example.peliculas.model.Result
import com.example.peliculas.webservice.ApiService
import com.example.peliculas.webservice.RetrofitClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
    private lateinit var description: TextView
    private lateinit var producersText: TextView
    private lateinit var writersText: TextView
    private lateinit var releaseDate: TextView

    private var id: String = ""
    private lateinit var movie: Result

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_activity)
        imgMovie = findViewById(R.id.movieImage)
        movieTitle = findViewById(R.id.movieTitle)
        rantingMovie = findViewById(R.id.rantingMovie)
        studios = findViewById(R.id.studios)
        description = findViewById(R.id.description)
        producersText = findViewById(R.id.producerstext)
        writersText = findViewById(R.id.writersText)
        releaseDate = findViewById(R.id.releaseDate)

        //Trae el id del la pel??cula que fue seleccionada
        id = intent.getStringExtra("id").toString()

        //llama a la funci??n para llamar a la api
        val ok = getOneMovie()
        if (!ok) {
            //en caso de no haber internet llama a la funcion del json local
            movie = intent.getStringExtra("id")?.let { returnJsonInList(it.toInt()) }!!
            updateUi(movie)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        id = ""
    }

    private fun getOneMovie(): Boolean {
        var ok = false
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
                            ok = true
                        }
                    }
                }
            })
        }
        return ok
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateUi(movie: Result) {
        Picasso.get().load(movie.image.medium_url).into(imgMovie)
        if (movie.name != null)
        movieTitle.text = movie.name
        if (movie.rating != null)
        rantingMovie.text = ("Rating: " + movie.rating)
        if (movie.release_date != null)
            releaseDate.text = ("Release date: ${movie.release_date}")
        if (movie.studios != null)
            studios.text = ("Studios: " + strings(movie.studios))
        if (movie.producers != null) producersText.text =
            ("Producers: ${producers(movie.producers)}")
        if (movie.writers != null) writersText.text =
            ("Writers: ${writers(movie.writers)}")
        if (movie.description != null) description.text =
            ("Description: \n" + Html.fromHtml(movie.description, Html.FROM_HTML_MODE_COMPACT)
                .toString())
    }

    fun returnJsonInList(id: Int): Result {
        //utiliza la funci??n de Utils.kt enviandole el contexto y el nombre del archivo.
        val jsonFileString = getJsonDataFromAssets(applicationContext, "movie_data.json")
        val gson = Gson()
        /*guarda el tipo de dato en el cual se va a convertir la inforacion del json a un listado de
        objetos de Poi
        */
        val listMovies = object : TypeToken<Data>() {}.type
        //Transforma el json en el arreglo de Poi
        val movies: Data = gson.fromJson(jsonFileString, Data::class.java)
        movies.results
        return movies.results[id - 1]
    }

}
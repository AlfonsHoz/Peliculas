package com.example.peliculas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.peliculas.helpers.getJsonDataFromAssets
import com.example.peliculas.model.Data
import com.example.peliculas.model.Result
import com.example.peliculas.recyclerMoviesList.MoviesAdapter
import com.example.peliculas.recyclerMoviesList.OnItemClickListener
import com.example.peliculas.webservice.ApiService
import com.example.peliculas.webservice.RetrofitClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var adapter: MoviesAdapter
    private var movies = mutableListOf<Result>()

    @RequiresApi(Build.VERSION_CODES.R)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movies = returnJsonInList()
        getAllMovies()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        adapter = MoviesAdapter(movies, this)
        val rvMovies = findViewById<RecyclerView>(R.id.rvMovies)
        rvMovies.adapter = adapter
        rvMovies.layoutManager = LinearLayoutManager(this)
    }

    private fun getAllMovies() {
        CoroutineScope(IO).launch {
            val call = RetrofitClass.getRetrofit().create(ApiService::class.java)
            val result: Call<Data> = call.getApiData()
            var resp: MutableList<Result>
            result.enqueue(object : Callback<Data> {
                override fun onFailure(call: Call<Data>, t: Throwable) {
                    Log.e("Error Api", t.toString())
                }

                @RequiresApi(Build.VERSION_CODES.M)
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<Data>, response: Response<Data>) {
                    resp = response.body()!!.results as MutableList<Result>
                    runOnUiThread {
                        if (result.isExecuted) {
                            movies.clear()
                            movies.addAll(resp)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            })
        }
    }

    override fun onItemClick(position: Int) {
        val clickedItem: Result = movies[position]
        val intent = Intent(this, MovieActivity::class.java)
        intent.putExtra("id", clickedItem.id.toString())
        startActivity(intent)
    }

    fun returnJsonInList(): MutableList<Result> {
        //utiliza la función de Utils.kt enviandole el contexto y el nombre del archivo.
        val jsonFileString = getJsonDataFromAssets(applicationContext, "movie_data.json")
        val gson = Gson()

        /*guarda el tipo de dato en el cual se va a convertir la inforacion del json a un listado de
        objetos de Poi
        */
        val listMovies = object: TypeToken<Data>(){}.type

        //Transforma el json en el arreglo de Poi
        val movies: Data = gson.fromJson(jsonFileString, Data::class.java)
        return movies.results as MutableList<Result>
    }
}
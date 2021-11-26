package com.example.peliculas.webservice

import com.example.peliculas.model.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("?api_key=f188f06dcfce9af860890eba458ed5d5242ec43b&format=json")
    fun getApiData(): Call<Data>

    @GET("?api_key=f188f06dcfce9af860890eba458ed5d5242ec43b&format=json")
    fun getOneMovieFromApi(@Query("filter") id: String): Call<Data>
}
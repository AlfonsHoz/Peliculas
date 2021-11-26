package com.example.peliculas.webservice

import com.example.peliculas.model.Data
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("?api_key=f188f06dcfce9af860890eba458ed5d5242ec43b&format=json")
    fun getApiData(): Call<Data>
}
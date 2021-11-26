package com.example.peliculas.helpers

import android.content.Context
import com.example.peliculas.model.Producer
import com.example.peliculas.model.Studio
import com.example.peliculas.model.Writer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun strings(list: List<Studio>): String {
    var res = ""
    list.forEachIndexed { idx, item: Studio ->
        res += if (idx != list.size - 1) (item.name + ", ")
        else item.name
    }
    return res
}

fun producers(list: List<Producer>): String {
    var res = ""
    list.forEachIndexed { idx, item: Producer ->
        res += if (idx != list.size - 1) (item.name + ", ")
        else item.name
    }
    return res
}

fun writers(list: List<Writer>): String {
    var res = ""
    list.forEachIndexed { idx, item: Writer ->
        res += if (idx != list.size - 1) (item.name + ", ")
        else item.name
    }
    return res
}

//funcipon para traer la información de un json
fun getJsonDataFromAssets(context: Context, fileName: String): String {
    var jsonString: String = ""
    try {
        //abre y lee el archivo json y la guarda en la variable
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (e: Exception) {
        println(e)
    }
    return jsonString
}



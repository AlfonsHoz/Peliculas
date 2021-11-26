package com.example.peliculas.helpers

import com.example.peliculas.model.Producer
import com.example.peliculas.model.Studio
import com.example.peliculas.model.Writer

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

fun writers(list: List<Writer>): String{
    var res = ""
    list.forEachIndexed { idx, item: Writer ->
        res += if (idx != list.size - 1) (item.name + ", ")
        else item.name
    }
    return res
}
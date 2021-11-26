package com.example.peliculas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Data(
    val error: String,
    val limit: Int,
    val number_of_page_results: Int,
    val number_of_total_results: Int,
    val offset: Int,
    val results: List<Result>,
    val status_code: Int,
    val version: String
)

@Entity(tableName = "movies")
data class Result(
    @PrimaryKey
    val api_detail_url: String,
    val box_office_revenue: String,
    val budget: String,
    val date_added: String,
    val date_last_updated: String,
    val deck: String,
    val description: String,
    val distributor: Any,
    val has_staff_review: Any,
    val id: Int,
    val image: Image,
    val name: String,
    val producers: List<Producer>,
    val rating: String,
    val release_date: String,
    val runtime: String,
    val site_detail_url: String,
    val studios: List<Studio>,
    val total_revenue: String,
    val writers: List<Writer>
)


data class Image(
    val icon_url: String,
    val image_tags: String,
    val medium_url: String,
    val original_url: String,
    val screen_large_url: String,
    val screen_url: String,
    val small_url: String,
    val super_url: String,
    val thumb_url: String,
    val tiny_url: String
)


data class Producer(
    val api_detail_url: String,
    val id: Int,
    val name: String,
    val site_detail_url: String
)


data class Studio(
    val api_detail_url: String,
    val id: Int,
    val name: String,
    val site_detail_url: String
)


data class Writer(
    val api_detail_url: String,
    val id: Int,
    val name: String,
    val site_detail_url: String
)
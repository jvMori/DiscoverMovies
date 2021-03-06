package com.mori.jvmori.discovermovies.data.local.entity


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies_discover", primaryKeys = ["page", "genreId"])
data class DiscoverMovieResponse(
    @SerializedName("page")
    val page: Int,
    var genreId : Int,
    @SerializedName("results")
    val results: List<MovieResult>,
    @SerializedName("total_pages", alternate = [ "total_results"])
    val totalPages: Int,
    var timestamp : Long
)
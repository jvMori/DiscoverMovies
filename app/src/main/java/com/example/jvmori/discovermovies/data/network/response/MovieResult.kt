package com.example.jvmori.discovermovies.data.network.response

import com.google.gson.annotations.SerializedName

data class MovieResult(
    @SerializedName("id")
    val id: Int,
    var movieDetails : MovieDetails
)
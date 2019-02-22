package com.example.jvmori.discovermovies.data.network.response

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)
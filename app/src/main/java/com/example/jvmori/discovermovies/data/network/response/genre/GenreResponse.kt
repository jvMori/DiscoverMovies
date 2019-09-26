package com.example.jvmori.discovermovies.data.network.response.genre

import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)
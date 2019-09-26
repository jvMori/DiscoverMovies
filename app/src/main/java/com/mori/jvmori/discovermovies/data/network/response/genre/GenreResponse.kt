package com.mori.jvmori.discovermovies.data.network.response.genre

import com.mori.jvmori.discovermovies.data.local.entity.Genre
import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)
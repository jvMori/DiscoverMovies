package com.example.jvmori.discovermovies.data.network.response


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val idGenre: Int,
    @SerializedName("name")
    val name: String
)
package com.example.jvmori.discovermovies.data.network.response.video

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id")
    val id: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("site")
    val site: String
)
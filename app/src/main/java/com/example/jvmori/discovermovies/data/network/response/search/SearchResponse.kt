package com.example.jvmori.discovermovies.data.network.response.search

import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
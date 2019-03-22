package com.example.jvmori.discovermovies.data.network.response.recommendations

import com.example.jvmori.discovermovies.data.network.response.movie.MovieResult
import com.google.gson.annotations.SerializedName

data class RecommendationsResponse(
    val page: Int,
    val results: List<MovieResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
package com.mori.jvmori.discovermovies.data.network.response.recommendations

import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.google.gson.annotations.SerializedName

data class RecommendationsResponse(
    val page: Int,
    val results: List<MovieResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
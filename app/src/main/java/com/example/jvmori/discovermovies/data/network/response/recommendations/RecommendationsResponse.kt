package com.example.jvmori.discovermovies.data.network.response.recommendations

import com.google.gson.annotations.SerializedName

data class RecommendationsResponse(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
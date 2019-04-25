package com.example.jvmori.discovermovies.data.local.entity

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "saved_movies")
data class MovieResult(
    @SerializedName("id")
    val id: Int,
    val adult: Boolean,
    val media_type : String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String?,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)
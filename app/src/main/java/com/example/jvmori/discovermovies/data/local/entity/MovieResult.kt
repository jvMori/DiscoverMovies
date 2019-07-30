package com.example.jvmori.discovermovies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

@Entity(tableName = "saved_movies")
data class MovieResult(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val adult: Boolean,
    @SerializedName("media_type")
    var mediaType: String,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
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
    val voteCount: Int,
    var timestamp: Long,
    var category : String,
    var collection: String = "",
    var period : String
) {
    override fun equals(other: Any?): Boolean {
        if (other == this)
            return true
        if (other !is MovieResult)
            return false
        val movie = other as MovieResult
        return movie.id == id && movie.originalTitle == originalTitle
    }

}
enum class Category{
    TRENDING, NOW_PLAYING, NONE
}

enum class Collection{
    LIKES,
    NONE
}


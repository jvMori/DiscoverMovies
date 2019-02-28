package com.example.jvmori.discovermovies.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jvmori.discovermovies.data.network.response.Genre
import com.google.gson.annotations.SerializedName
import java.time.ZonedDateTime

@Entity(tableName = "movie_table")
data class GenreEntry(
    @PrimaryKey(autoGenerate = false)
    val id : Int = 0,
    @SerializedName("genres")
    val genres: List<Genre>,
    var fetchTime : ZonedDateTime
)
package com.example.jvmori.discovermovies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
data class Genre(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val idGenre: Int,
    @SerializedName("name")
    val name: String
)
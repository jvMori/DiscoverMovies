package com.example.jvmori.discovermovies.data.local.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "collection_table")
data class CollectionType (
    @PrimaryKey(autoGenerate = false)
    var colName : String = "",

    @Ignore
    var listOfMovies : List<MovieResult> = mutableListOf()
)

enum class Collection{
    LIKES,
    TO_WATCH,
    WATCHED,
    NONE
}

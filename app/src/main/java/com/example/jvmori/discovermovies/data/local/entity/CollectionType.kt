package com.example.jvmori.discovermovies.data.local.entity

import androidx.room.Entity
import androidx.room.Ignore

@Entity(tableName = "collection_table")
data class CollectionType (
    val colName : String,

    @Ignore
    var listOfMovies : List<MovieResult>
)

enum class Collection{
    LIKES,
    TO_WATCH,
    WATCHED,
    NONE
}

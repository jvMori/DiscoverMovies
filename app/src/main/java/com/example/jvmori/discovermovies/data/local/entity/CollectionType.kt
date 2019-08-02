package com.example.jvmori.discovermovies.data.local.entity

import androidx.room.Entity

@Entity(tableName = "collection_table")
data class CollectionType (
    val colName : String
)

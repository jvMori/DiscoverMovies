package com.example.jvmori.discovermovies.data.local.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "saved_coll_table")
data class CollectionData (
    @PrimaryKey(autoGenerate = false)
    val collectionName : String,
    val collectionId : Int
)



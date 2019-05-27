package com.example.jvmori.discovermovies.data.repository.collection

import androidx.lifecycle.LiveData
import com.example.jvmori.discovermovies.data.local.entity.MovieResult

interface CollectionRepository {
    fun displayAllSaved(collection : String): LiveData<List<MovieResult>>
}
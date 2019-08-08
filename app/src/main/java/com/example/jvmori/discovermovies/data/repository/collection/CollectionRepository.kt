package com.example.jvmori.discovermovies.data.repository.collection

import com.example.jvmori.discovermovies.data.local.entity.CollectionData
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import io.reactivex.Observable

interface CollectionRepository {
    fun displayAllSaved(collection : CollectionData): Observable<List<MovieResult>>
    fun insert(nameOfCollection : CollectionData)
    fun delete(nameOfCollection: CollectionData)
    fun getAllCollectionsNames() : Observable<List<CollectionData>>
}
package com.example.jvmori.discovermovies.data.repository.collection

import com.example.jvmori.discovermovies.data.local.entity.CollectionType
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import io.reactivex.Observable

interface CollectionRepository {
    fun displayAllSaved(collection : CollectionType): Observable<List<MovieResult>>
    fun insert(nameOfCollection : CollectionType)
    fun delete(nameOfCollection: CollectionType)
    fun getAllCollectionsNames() : Observable<CollectionType>
}
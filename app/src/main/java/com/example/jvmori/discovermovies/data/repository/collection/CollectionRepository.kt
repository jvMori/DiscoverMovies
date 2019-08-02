package com.example.jvmori.discovermovies.data.repository.collection

import com.example.jvmori.discovermovies.data.local.entity.CollectionType
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import io.reactivex.Observable

interface CollectionRepository {
    fun displayAllSaved(collection : String): Observable<List<MovieResult>>
    fun insert(nameOfCollection : String)
    fun delete(nameOfCollection: String)
    fun getAllCollectionsNames() : Observable<CollectionType>
}
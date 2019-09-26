package com.mori.jvmori.discovermovies.data.repository.collection

import com.mori.jvmori.discovermovies.data.local.entity.CollectionData
import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import io.reactivex.Observable

interface CollectionRepository {
    fun displayAllSaved(collection : CollectionData): Observable<List<MovieResult>>
    fun insert(nameOfCollection : CollectionData)
    fun delete(nameOfCollection: CollectionData)
    fun getAllCollectionsNames() : Observable<List<CollectionData>>
}
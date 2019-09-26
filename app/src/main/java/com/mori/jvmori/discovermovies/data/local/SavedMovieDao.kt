package com.mori.jvmori.discovermovies.data.local

import androidx.room.*
import com.mori.jvmori.discovermovies.data.local.entity.CollectionData
import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface SavedMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieResult)

    @Delete
    fun delete(movie: MovieResult)

    @Query("Select * from saved_movies where collection like :collectionName")
    fun getAllSaved(collectionName: String): Observable<List<MovieResult>>

    @Query("Select * from saved_movies where id like :movieId")
    fun getMovie(movieId: Int): Single<MovieResult>

    @Query("Select * from saved_movies where id like :movieId AND collection like:collectionName")
    fun getMovieByIdAndCollection(movieId: Int, collectionName: String): Single<MovieResult>

    @Query("Delete from saved_movies where category like:categoryName AND period like:periodTime")
    fun deleteFromCategory(periodTime: String, categoryName : String)

    @Transaction
    fun updateMovies(periodTime: String, categoryName : String, movies: List<MovieResult>) {
        deleteFromCategory(periodTime, categoryName)
        movies.forEach {
            insert(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollection(collection : CollectionData)

    @Query("SELECT * FROM saved_coll_table where collectionId like 0 ORDER BY collectionName")
    fun getAllCollections() : Observable<List<CollectionData>>

    @Delete
    fun delete(collection : CollectionData)
}
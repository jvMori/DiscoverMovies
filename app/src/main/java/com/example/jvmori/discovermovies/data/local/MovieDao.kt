package com.example.jvmori.discovermovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse
import io.reactivex.Maybe

@Dao
interface MovieDao
{
    @Insert
    fun insert(movies : DiscoverMovieResponse)

    @Transaction
    fun updateData(movies : DiscoverMovieResponse){
        deleteMovies(movies.genreId, movies.page)
        insert(movies)
    }

    @Query("Delete from movies_discover where genreId like :genreId and page like :page")
    fun deleteMovies(genreId : Int, page: Int)

    @Query("Select * from movies_discover where genreId like :genreId and page like :page")
    fun getMovies(genreId : Int, page: Int) : Maybe<DiscoverMovieResponse>
}
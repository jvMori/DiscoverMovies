package com.example.jvmori.discovermovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse

@Dao
interface MovieDao
{
    @Insert
    fun insert(movies : DiscoverMovieResponse)

    @Transaction
    fun updateData(movies : DiscoverMovieResponse){
        deleteUsers(movies.genreId, movies.page)
        insert(movies)
    }
    
    @Query("Delete from movies_discover where genreId like :genreId and page like :page")
    fun deleteUsers(genreId : Int, page: Int)
}
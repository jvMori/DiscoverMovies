package com.example.jvmori.discovermovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import com.example.jvmori.discovermovies.data.local.entity.DiscoverMovieResponse

@Dao
interface MovieDao
{
    @Insert
    fun insert(movies : DiscoverMovieResponse)
}
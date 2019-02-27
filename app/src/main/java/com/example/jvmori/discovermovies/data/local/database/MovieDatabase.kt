package com.example.jvmori.discovermovies.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jvmori.discovermovies.data.local.GenreDao
import com.example.jvmori.discovermovies.data.local.entity.Genre

@Database(entities = [Genre::class], version = 1 )
abstract class MovieDatabase : RoomDatabase(){
    abstract fun genreDao() : GenreDao

    companion object {
        @Volatile private var instance: MovieDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context,
                MovieDatabase::class.java, "movies.db")
                .build()
    }
}
package com.example.jvmori.discovermovies.data.local.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.jvmori.discovermovies.data.local.GenreDao
import com.example.jvmori.discovermovies.data.local.MovieDao
import com.example.jvmori.discovermovies.data.local.SavedMovieDao
import com.example.jvmori.discovermovies.data.local.entity.*
import com.example.jvmori.discovermovies.util.Converters
import com.example.jvmori.discovermovies.data.local.Collection

@Database(entities = [
    Genre::class,
    DiscoverMovieResponse::class,
    MovieResult::class,
    CollectionData::class
], version = 15, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun moviesDao(): MovieDao
    abstract fun savedMovieDao() : SavedMovieDao


    companion object {
        @Volatile
        private var instance: MovieDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun prePopulate() : RoomDatabase.Callback{
            return object : RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    instance?.savedMovieDao()?.insertCollection(CollectionData(Collection.LIKES.toString(),0) )
                    instance?.savedMovieDao()?.insertCollection(CollectionData(Collection.WATCHED.toString(),0))
                    instance?.savedMovieDao()?.insertCollection(CollectionData(Collection.TO_WATCH.toString(),0))
                }
            }
        }


        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                MovieDatabase::class.java, "movies.db"
            )
                .addCallback(prePopulate())
                .fallbackToDestructiveMigration()
                .build()

    }
}
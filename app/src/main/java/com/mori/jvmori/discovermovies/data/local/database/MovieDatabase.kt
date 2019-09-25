package com.mori.jvmori.discovermovies.data.local.database

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mori.jvmori.discovermovies.data.local.GenreDao
import com.mori.jvmori.discovermovies.data.local.MovieDao
import com.mori.jvmori.discovermovies.data.local.SavedMovieDao
import com.mori.jvmori.discovermovies.data.local.entity.*
import com.mori.jvmori.discovermovies.util.Converters
import com.mori.jvmori.discovermovies.data.local.Collection
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

@Database(entities = [
    Genre::class,
    DiscoverMovieResponse::class,
    MovieResult::class,
    CollectionData::class
], version = 28, exportSchema = false)
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
                    Completable.fromAction {
                        instance?.savedMovieDao()?.insertCollection(CollectionData(Collection.getName(Collection.LIKES),0, false) )
                        instance?.savedMovieDao()?.insertCollection(CollectionData(Collection.getName(Collection.WATCHED),0, false))
                        instance?.savedMovieDao()?.insertCollection(CollectionData(Collection.getName(Collection.TO_WATCH),0, false))
                    }.subscribeOn(Schedulers.io())
                        .doOnError {
                            Log.i("error", it.message)
                        }
                        .subscribe()
                }
            }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                MovieDatabase::class.java, "discoverMoviesNew.db"
            )
                .addCallback(prePopulate())
                .fallbackToDestructiveMigration()
                .build()

    }
}
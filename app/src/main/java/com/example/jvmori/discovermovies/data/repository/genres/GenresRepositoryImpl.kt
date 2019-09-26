package com.example.jvmori.discovermovies.data.repository.genres

import android.content.Context
import android.util.Log
import com.example.jvmori.discovermovies.data.local.GenreDao
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.network.TmdbAPI
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    var tmdbAPI: TmdbAPI,
    var context: Context,
    private var genreDao: GenreDao
) : GenresRepository {

    override fun getAllGenresLocal(): Maybe<List<Genre>> {
        return genreDao.getAllGenres()
    }

    override fun getGenres(): Observable<List<Genre>> {
        return Maybe.concat(getAllGenresLocal(), getAllGenresRemote())
            .filter { list ->
                list.isNotEmpty()
            }
            //.take(1)
            .toObservable()
    }
    override fun getGenreById(genreId: Int): Single<Genre> {
        return genreDao.getGenre(genreId)
    }

    private fun getAllGenresRemote(): Maybe<List<Genre>> {
        return tmdbAPI.getGenres()
            .flatMap {
                return@flatMap Maybe.just(it.genres)
            }.doAfterSuccess {
                saveData(it)
            }
            .doOnError {
                Log.i("Error", "Error")
            }
            .subscribeOn(Schedulers.io())
    }

    private fun saveData(data: List<Genre>) {
        Completable.fromAction {
            genreDao.insert(data)
        }.subscribeOn(Schedulers.io())
            .subscribe()
    }
}
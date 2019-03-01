package com.example.jvmori.discovermovies.ui.view.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.jvmori.discovermovies.data.network.response.DiscoverMovieResponse
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.data.repository.MoviesRepository
import com.example.jvmori.discovermovies.ui.view.movies.DiscoverQueryParam
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MoviesDataSource(
    private val repository : MoviesRepository,
    private val parameters : DiscoverQueryParam
)  : PageKeyedDataSource<Int, MovieResult>(
){
    val firstPage : Int = parameters.page

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieResult>) {
        repository.getMoviesToDiscover(parameters)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object: DisposableObserver<DiscoverMovieResponse>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: DiscoverMovieResponse) {
                       callback.onResult(t.results, null, firstPage + 1 )
                    }

                    override fun onError(e: Throwable) {
                        Log.i("Error", e.message)
                    }

                }
            )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResult>) {
        repository.getMoviesToDiscover(DiscoverQueryParam(parameters.genresId, params.key))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object: DisposableObserver<DiscoverMovieResponse>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: DiscoverMovieResponse) {
                        var key : Int? = null
                        if (params.key <  t.totalPages) key = params.key + 1
                        callback.onResult(t.results, key )
                    }

                    override fun onError(e: Throwable) {
                        Log.i("Error", e.message)
                    }

                }
            )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResult>) {
        repository.getMoviesToDiscover(DiscoverQueryParam(parameters.genresId, params.key))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object: DisposableObserver<DiscoverMovieResponse>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: DiscoverMovieResponse) {
                        var key : Int? = null
                        if (params.key > 1 ) key = params.key - 1
                        callback.onResult(t.results, key )
                    }

                    override fun onError(e: Throwable) {
                        Log.i("Error", e.message)
                    }

                }
            )
    }

}
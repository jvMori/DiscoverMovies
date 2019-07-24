package com.example.jvmori.discovermovies.ui.presenter.collections

import com.example.jvmori.discovermovies.data.local.entity.Category
import com.example.jvmori.discovermovies.data.local.entity.Collection
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.repository.movies.MoviesRepository
import com.example.jvmori.discovermovies.ui.adapters.MoviesAdapter
import com.example.jvmori.discovermovies.util.Const
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SavingBasePresenterImpl @Inject constructor(
    private var repository: MoviesRepository
) : SavingBasePresenter,  MoviesAdapter.OnFavIconClickListener {

    private val disposable = CompositeDisposable()
    private lateinit var view : SavingView

    override fun <T> setView(view: T) {
        this.view = view as SavingView
    }

    override fun dispose() {
        disposable.clear()
    }

    override fun saveMovie(movieResult: MovieResult) {
        disposable.add(
            repository.getMovieFromDbByIdAndCategory(movieResult, Collection.LIKES.toString())
                .subscribe({ success ->
                    repository.deleteMovie(movieResult)
                    view.displayDeletedIcon()
                }, { error ->
                    repository.saveMovie(movieResult, Collection.LIKES.toString(), Category.NONE.toString(), "week")
                    view.displaySavedIcon()
                })
        )
    }

    override fun onFavClicked(movieResult: MovieResult) {
        movieResult.mediaType = Const.MOVIE
        saveMovie(movieResult)
    }
}
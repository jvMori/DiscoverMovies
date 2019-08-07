package com.example.jvmori.discovermovies.ui.presenter.collections

import com.example.jvmori.discovermovies.data.local.entity.Category
import com.example.jvmori.discovermovies.data.local.entity.Collection
import com.example.jvmori.discovermovies.data.local.entity.CollectionType
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.repository.collection.CollectionRepository
import com.example.jvmori.discovermovies.data.repository.movies.MoviesRepository
import com.example.jvmori.discovermovies.ui.adapters.MoviesAdapter
import com.example.jvmori.discovermovies.util.Const
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SavingBasePresenterImpl @Inject constructor(
    private var repository: MoviesRepository,
    private var repositoryCol: CollectionRepository
) : SavingBasePresenter {

    private val disposable = CompositeDisposable()
    private lateinit var view: SavingView

    override fun <T> setView(view: T) {
        this.view = view as SavingView
    }

    override fun dispose() {
        disposable.clear()
    }

    override fun saveMovie(movieResult: MovieResult, collection: String) {
        disposable.add(
            repository.getMovieFromDbByIdAndCategory(movieResult, collection)
                .subscribe({ success ->
                    repository.deleteMovie(movieResult)
                    view.displayDeletedIcon()
                }, { error ->
                    repository.saveMovie(movieResult, collection, Category.NONE.toString(), "week")
                    repositoryCol.insert(CollectionType(collection))
                    view.displaySavedIcon()
                })
        )
    }

    override fun updateFavIcon(movieResult: MovieResult) {
        disposable.add(
            repository.getMovieFromDbByIdAndCategory(movieResult, Collection.LIKES.toString())
                .subscribe(
                    {
                        view.displayDeletedIcon()
                    },
                    {
                        view.displaySavedIcon()
                    }
                )
        )
    }
}
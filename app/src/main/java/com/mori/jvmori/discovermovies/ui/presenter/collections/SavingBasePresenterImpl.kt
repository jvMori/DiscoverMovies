package com.mori.jvmori.discovermovies.ui.presenter.collections

import com.mori.jvmori.discovermovies.data.local.entity.Category
import com.mori.jvmori.discovermovies.data.local.Collection
import com.mori.jvmori.discovermovies.data.local.entity.CollectionData
import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.data.repository.collection.CollectionRepository
import com.mori.jvmori.discovermovies.data.repository.movies.MoviesRepository
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
                .subscribe({
                    repository.deleteMovie(movieResult)
                    view.displayDeletedIcon()
                }, {
                    repository.saveMovie(movieResult, collection, Category.NONE.toString(), "week")
                    repositoryCol.insert(CollectionData(collection,0, false))
                    view.displaySavedIcon()
                })
        )
    }

    override fun checkIsInCollection(items : List<CollectionData>, movieResult: MovieResult){
        items.forEachIndexed { index, collectionData ->
            disposable.add(
                repository.getMovieFromDbByIdAndCategory(movieResult, collectionData.collectionName)
                    .subscribe(
                        {
                            view.showCheckedIcon(index)
                        }, {
                            view.showUncheckedIcon(index)
                        }
                    )
            )
        }
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
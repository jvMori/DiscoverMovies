package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.ui.view.movies.MoviesPresenterInterface
import com.example.jvmori.discovermovies.util.LoadImage
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(
    private var moviesPresenter: MoviesPresenterInterface
) : PagedListAdapter<MovieResult, MoviesAdapter.MovieViewHolder>(MovieDiffCallback) {

    companion object {
        val MovieDiffCallback = object : DiffUtil.ItemCallback<MovieResult>() {
            override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it, presenter = moviesPresenter)
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieResult: MovieResult, presenter: MoviesPresenterInterface) {
            itemView.title.text = movieResult.title
            itemView.year.text = movieResult.releaseDate
            itemView.rating.text = movieResult.voteAverage.toString()
            itemView.review.text = movieResult.voteCount.toString()
            itemView.icon.clipToOutline = true
            itemView.category.text = ""
            movieResult.genreIds.forEachIndexed { index, item ->
                presenter.fetchGenreById(item).subscribe { response ->
                    itemView.category.append(response.name)
                    if (index != movieResult.genreIds.lastIndex)
                        itemView.category.append(" | ")
                }
            }
            LoadImage.loadImage(itemView.context, itemView.icon, movieResult.posterPath)
        }
    }
}
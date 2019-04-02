package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.network.response.movie.MovieResult
import com.example.jvmori.discovermovies.ui.IOnClickListener
import com.example.jvmori.discovermovies.ui.view.movies.MoviesPresenterInterface
import com.example.jvmori.discovermovies.util.Const
import com.example.jvmori.discovermovies.util.LoadImage
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(
    private var moviesPresenter: MoviesPresenterInterface,
    private var onClickListener: IOnClickListener?
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

        fun setStars(rating: Double, starsLayout: LinearLayout) {
            val maxNumberOfStars = starsLayout.childCount
            val ratingFromPercentage = rating * maxNumberOfStars / 100
            val starsCount = Math.floor(ratingFromPercentage)
            val halfStar = Math.round(ratingFromPercentage).toDouble()

            for (i in 0 until starsLayout.childCount) {
                val imageView = starsLayout.getChildAt(i) as ImageView
                if (i < starsCount) {
                    imageView.setImageResource(R.drawable.ic_star)
                }
                if (halfStar != starsCount && i.toDouble() == halfStar - 1) {
                    imageView.setImageResource(R.drawable.ic_star_half)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it, presenter = moviesPresenter)
            holder.itemView.setOnClickListener { onClickListener?.onMovieItemClicked(currentItem.id) }
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieResult: MovieResult, presenter: MoviesPresenterInterface) {
            itemView.titleItem.text = movieResult.title
            itemView.yearItem.text = movieResult.releaseDate
            itemView.ratingItem.text = movieResult.voteAverage.toString()
            itemView.reviewItem.text = movieResult.voteCount.toString()
            itemView.iconItem.clipToOutline = true
            itemView.categoryItem.text = ""
            movieResult.genreIds.forEachIndexed { index, item ->
                presenter.fetchGenreById(item).subscribe { response ->
                    itemView.categoryItem.append(response.name)
                    if (index != movieResult.genreIds.lastIndex)
                        itemView.categoryItem.append(" | ")
                }
            }
            LoadImage.loadImage(itemView.context, itemView.iconItem, Const.base_poster_url + movieResult.posterPath)
            MoviesAdapter.setStars(movieResult.voteAverage * 10, itemView.layoutStars)
        }
    }
}
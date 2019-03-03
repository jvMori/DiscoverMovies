package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.network.response.MovieDetails
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.util.LoadImage
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(
    private val movieItems: MutableList<MovieResult> = mutableListOf()
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

    fun getItemPosition(movieResult: MovieResult): Int {
        return movieItems.indexOf(movieResult)
    }

    fun setItem(position: Int?, movieResult: MovieDetails) {
        position.let {
            movieItems[position!!].movieDetails = movieResult
        }
    }

    override fun getItemCount(): Int {
        super.getItemCount()
        return movieItems.size
    }

    override fun getItem(position: Int): MovieResult? {
        return super.getItem(position)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movieItems[position]
        val details: MovieDetails? = currentItem.movieDetails
        details?.let {
            holder.bind(details)
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(details: MovieDetails) {
            itemView.title.text = details.title
            itemView.year.text = details.releaseDate
            itemView.rating.text = details.voteAverage.toString()
            itemView.review.text = details.voteCount.toString()
            itemView.icon.clipToOutline = true
            itemView.category.text = ""
            details.genres.forEachIndexed { index, item ->
                itemView.category.append(item.name)
                if (index != details.genres.lastIndex)
                    itemView.category.append(" | ")
            }
            LoadImage.loadImage(itemView.context, itemView.icon, details.posterPath)
        }
    }
}
package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.datasource.NetworkState
import com.example.jvmori.discovermovies.data.network.response.MovieDetails
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.util.LoadImage
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter: PagedListAdapter<MovieResult, MoviesAdapter.MovieViewHolder>(MovieDiffCallback) {

    private var networkState: NetworkState? = null

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

    fun setItem(movieResult: MovieDetails){
        currentList?.forEach{
            if (it.id == movieResult.id) {
                it.movieDetails = movieResult
            }
        }
    }
    override fun getItemCount(): Int {
        return super.getItemCount() + if(hasExtraRow()) 1 else 0
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        val details: MovieDetails? = currentItem?.movieDetails
        details?.let {
            holder.bind(details)
        }
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        if (currentList != null) {
            if (currentList!!.size != 0) {
                val previousState = this.networkState
                val hadExtraRow = hasExtraRow()
                this.networkState = newNetworkState
                val hasExtraRow = hasExtraRow()
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount())
                    } else {
                        notifyItemInserted(super.getItemCount())
                    }
                } else if (hasExtraRow && previousState !== newNetworkState) {
                    notifyItemChanged(itemCount - 1)
                }
            }
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
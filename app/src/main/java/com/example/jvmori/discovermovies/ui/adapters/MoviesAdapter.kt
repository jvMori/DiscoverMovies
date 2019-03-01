package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.network.response.MovieDetails
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.util.LoadImage
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(
    private val movieItems: MutableList<MovieResult> = mutableListOf()
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }
    fun getItemPosition(movieResult : MovieResult) : Int{
        return movieItems.indexOf(movieResult)
    }

    fun setItem(position: Int?, movieResult: MovieDetails){
        position.let {
           movieItems[position!!].movieDetails = movieResult
        }
    }
    override fun getItemCount(): Int = movieItems.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movieItems[position]
        val details: MovieDetails? = currentItem.movieDetails
        details?.let {
            holder.itemView.title.text = details.title
            holder.itemView.year.text = details.releaseDate
            holder.itemView.rating.text = details.voteAverage.toString()
            holder.itemView.review.text = details.voteCount.toString()
            holder.itemView.icon.clipToOutline = true
            holder.itemView.category.text= ""
            details.genres.forEachIndexed { index, item ->
                holder.itemView.category.append(item.name)
                if (index != details.genres.lastIndex)
                    holder.itemView.category.append(" | ")
            }
            LoadImage.loadImage(holder.itemView.context, holder.itemView.icon, details.posterPath)
        }
    }
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
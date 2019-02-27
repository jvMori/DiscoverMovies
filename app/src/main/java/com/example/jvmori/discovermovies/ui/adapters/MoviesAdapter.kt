package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.network.response.MovieResult
import com.example.jvmori.discovermovies.util.LoadImage
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(
    private var movieItems: List<MovieResult>,
    private var genreList: List<Genre>
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movieItems.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movieItems[position]
//        holder.itemView.title.text = currentItem.title
//        holder.itemView.year.text = currentItem.releaseDate
//        holder.itemView.rating.text = currentItem.voteAverage.toString()
//        holder.itemView.review.text = currentItem.voteCount.toString()
//        holder.itemView.icon.clipToOutline = true
//        currentItem.genreIds.forEachIndexed { index, item ->
//            holder.itemView.category.append(genreList[item].name)
//            if (index != currentItem.genreIds.lastIndex)
//                holder.itemView.category.append(" | ")
//        }
//        LoadImage.loadImage(holder.itemView.icon, currentItem.posterPath)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
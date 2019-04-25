package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.util.Const
import com.example.jvmori.discovermovies.util.LoadImage
import kotlinx.android.synthetic.main.cast_item.view.*

class SimilarMoviesAdapter : BaseAdapter<MovieResult>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MovieResult> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cast_item, parent, false)
        return MovieViewHolder(view)
    }

    class MovieViewHolder(itemView: View) : BaseViewHolder<MovieResult>(itemView){
        override fun bindView(item : MovieResult){
            itemView.title.text = item.title
            LoadImage.loadImage(itemView.context, itemView.icon, Const.base_poster_url + item.posterPath)
        }
    }
}
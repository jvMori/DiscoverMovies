package com.mori.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mori.jvmori.discovermovies.R
import com.mori.jvmori.discovermovies.data.local.entity.MovieResult
import com.mori.jvmori.discovermovies.util.Const
import com.mori.jvmori.discovermovies.util.LoadImage
import kotlinx.android.synthetic.main.cast_item.view.*

class SimilarMoviesAdapter : BaseAdapter<MovieResult>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MovieResult> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cast_item, parent, false)
        return MovieViewHolder(view, iOnItemClickListener)
    }

    class MovieViewHolder(itemView: View, private var iOnItemClickListener: IOnItemClickListener<MovieResult>?) : BaseViewHolder<MovieResult>(itemView){
        override fun bindView(item : MovieResult){
            itemView.title.text = item.title
            LoadImage.loadImage(itemView.context, itemView.icon, Const.base_poster_url + item.posterPath)
            itemView.setOnClickListener{
                iOnItemClickListener?.onItemClicked(item)
            }
        }
    }
}
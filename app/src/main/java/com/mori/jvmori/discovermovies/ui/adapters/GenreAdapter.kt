package com.mori.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mori.jvmori.discovermovies.R
import com.mori.jvmori.discovermovies.data.local.entity.Genre
import com.mori.jvmori.discovermovies.util.RandomColor
import kotlinx.android.synthetic.main.genre_item.view.*

class GenreAdapter(
    private val onClickListener: IOnGenreClick?
) : BaseAdapter<Genre>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)
        return GenreViewHolder(view, onClickListener)
    }

    class GenreViewHolder(itemView: View, private val onClickListener: IOnGenreClick?) : BaseViewHolder<Genre>(itemView) {
        override fun bindView(item: Genre) {
            itemView.itemName.text = item.name
            itemView.cardView2.setCardBackgroundColor(RandomColor.generateColor())
            itemView.setOnClickListener {
                onClickListener?.onGenreClicked(item)
            }
        }
    }

    interface IOnGenreClick {
        fun onGenreClicked(item: Genre)
    }
}
package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.ui.view.discover.DiscoverFragmentDirections
import com.example.jvmori.discovermovies.util.RandomColor
import kotlinx.android.synthetic.main.genre_item.view.*

class GenreAdapter(
    private val onClickListener: IOnGenreClick?
) : BaseAdapter<Genre>() {

    override var iOnItemClickListener: IOnItemClickListener?
        get() = null
        set(value) {}

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
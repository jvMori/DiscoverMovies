package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jvmori.discovermovies.R
import kotlinx.android.synthetic.main.playlist_item.view.*

class AddToCollectionAdapter  : BaseAdapter<String>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
        return CollectionViewHolder(view, iOnItemClickListener)
    }

    class CollectionViewHolder(itemView : View, private var iOnItemClickListener: IOnItemClickListener<String>?) : BaseViewHolder<String>(itemView){
        override fun bindView(item: String) {
            itemView.nameOfPlaylist?.text = item
            itemView.setOnClickListener{
                iOnItemClickListener?.onItemClicked(item)
            }
        }
    }
}
package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.CollectionData
import kotlinx.android.synthetic.main.playlist_item.view.*

class AddToCollectionAdapter  : BaseAdapter<CollectionData>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CollectionData> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
        return CollectionViewHolder(view, iOnItemClickListener)
    }

    class CollectionViewHolder(itemView : View, private var iOnItemClickListener: IOnItemClickListener<CollectionData>?) :
        BaseViewHolder<CollectionData>(itemView){
        override fun bindView(item: CollectionData) {
            itemView.nameOfPlaylist?.text = item.collectionName
            itemView.setOnClickListener{
                iOnItemClickListener?.onItemClicked(item)
            }
        }
    }
}
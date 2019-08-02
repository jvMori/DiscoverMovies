package com.example.jvmori.discovermovies.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.CollectionType
import kotlinx.android.synthetic.main.collection_rv_item.view.*

class CollectionAdapter(var context : Context) : BaseAdapter<CollectionType>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CollectionType> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.collection_rv_item, parent, false)
        return CollectionViewHolder(view, context)
    }

    class CollectionViewHolder(itemView : View, var context: Context) : BaseViewHolder<CollectionType>(itemView){
        override fun bindView(item: CollectionType) {
            itemView.customSectionItem.setTitleText(item.colName)
            itemView.customSectionItem.setRecyclerView(context, item.listOfMovies)
        }
    }
}
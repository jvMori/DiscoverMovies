package com.example.jvmori.discovermovies.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.CollectionType
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import kotlinx.android.synthetic.main.collection_rv_item.view.*

class CollectionAdapter(var context : Context) : BaseAdapter<CollectionType>() {

    val mapOfCollections = mutableMapOf<String, List<MovieResult>>()
    lateinit var viewHolder : CollectionViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CollectionType> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.collection_rv_item, parent, false)
        return CollectionViewHolder(view, context, mapOfCollections)
    }

    class CollectionViewHolder(itemView : View, var context: Context, private val map : Map<String, List<MovieResult>>) : BaseViewHolder<CollectionType>(itemView){
        override fun bindView(item: CollectionType) {
            itemView.customSectionItem.setTitleText(item.colName)
            itemView.customSectionItem.setRecyclerView(context, map[item.colName])
        }

        fun update(movies : List<MovieResult>){
            itemView.customSectionItem.updateItems(movies)
        }
    }
}
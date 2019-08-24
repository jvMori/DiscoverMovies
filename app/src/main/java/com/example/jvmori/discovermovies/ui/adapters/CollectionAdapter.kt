package com.example.jvmori.discovermovies.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.local.entity.CollectionData
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import kotlinx.android.synthetic.main.collection_rv_item.view.*

class CollectionAdapter(var context : Context) : BaseAdapter<CollectionData>() {

    val mapOfCollections = mutableMapOf<String, List<MovieResult>>()
    lateinit var viewHolder : CollectionViewHolder
    var iOnMovieClicked : IOnItemClickListener<MovieResult>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CollectionData> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.collection_rv_item, parent, false)
        return CollectionViewHolder(view, context, mapOfCollections, iOnMovieClicked)
    }

    class CollectionViewHolder(itemView : View,
                               var context: Context,
                               private val map : Map<String, List<MovieResult>>,
                               private var iOnMovieClicked : IOnItemClickListener<MovieResult>?) : BaseViewHolder<CollectionData>(itemView){
        override fun bindView(item: CollectionData) {
            itemView.customSectionItem.setTitleText(item.collectionName)
            itemView.customSectionItem.setRecyclerView(context, map[item.collectionName])
            itemView.customSectionItem.setIOnItemClickedListener(iOnMovieClicked)
        }

        fun update(movies : List<MovieResult>){
            itemView.customSectionItem.updateItems(movies)
        }
    }
}
package com.mori.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mori.jvmori.discovermovies.R
import com.mori.jvmori.discovermovies.data.local.entity.CollectionData
import kotlinx.android.synthetic.main.playlist_item.view.*

class AddToCollectionAdapter  : BaseAdapter<CollectionData>(){

    private lateinit var collectionViewHolder: CollectionViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CollectionData> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
        collectionViewHolder = CollectionViewHolder(view, iOnItemWithIdClickListener)
        return collectionViewHolder
    }

    fun changeData(position: Int, checked : Boolean){
        getItems()[position].isChecked = checked
        notifyItemChanged(position)
    }

    class CollectionViewHolder(itemView : View, private var iOnItemClickListener: IOnItemWithIdClickListener<CollectionData>?) :
        BaseViewHolder<CollectionData>(itemView){

        override fun bindView(item: CollectionData) {
            itemView.nameOfPlaylist?.text = item.collectionName
            val img = if(item.isChecked ) R.drawable.ic_check_box else R.drawable.ic_check_box_outline
            itemView.checkbox.setImageResource(img)
            itemView.setOnClickListener{
                iOnItemClickListener?.onItemWithIndexClicked(item, adapterPosition)
            }
        }
    }
}